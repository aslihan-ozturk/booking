package com.vngrs.booking.service;

import com.vngrs.booking.enums.AppointmentState;
import com.vngrs.booking.enums.TransactionState;
import com.vngrs.booking.enums.TransactionType;
import com.vngrs.booking.exception.BusinessException;
import com.vngrs.booking.exception.ValidationException;
import com.vngrs.booking.model.Appointment;
import com.vngrs.booking.model.Transaction;
import com.vngrs.booking.model.requestDto.CancelAppointmentRequestDTO;
import com.vngrs.booking.model.requestDto.GetAppointmentFeeRequestDTO;
import com.vngrs.booking.repository.AppointmentRepository;
import com.vngrs.booking.repository.DoctorRepository;
import com.vngrs.booking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AppointmentService {

    @Autowired
     AppointmentRepository appointmentRepository;
    @Autowired
     DoctorRepository doctorRepository;
    @Autowired
     TransactionRepository transactionRepository;




    public Double getAppointmentFee(GetAppointmentFeeRequestDTO requestDTO) throws ValidationException, BusinessException {
        isAppointmentAvailable(requestDTO.getDoctorId(), requestDTO.getPatientId(), requestDTO.getStartDate(), requestDTO.getEndDate());
        Double hourlyRate = doctorRepository.getHourlyRate(requestDTO.getDoctorId());
        Long duration = (requestDTO.getEndDate().getTime() - requestDTO.getStartDate().getTime()) / 3600000 ;
        if(duration < 1){
            throw new ValidationException("Appointments cant be shorter than an hour");
        }
        return duration * hourlyRate;
    }

    @Transactional
    public Appointment saveAppointment(Appointment appointment) throws ValidationException, BusinessException {
        isAppointmentAvailable(appointment.getDoctorId(), appointment.getPatientId(), appointment.getStartDate(), appointment.getEndDate());
        appointment.setState(AppointmentState.ACTIVE.getValue());
        appointment = appointmentRepository.save(appointment);
        Transaction transaction = new Transaction();
        transaction.setAppointmentId(appointment.getAppointmentId());
        transaction.setTransactionType(TransactionType.SAVE_APPOINTMENT.getValue());
        transaction.setPrice(appointment.getFee());
        transaction.setTransactionDate(new Timestamp(new Date().getTime()));
        transaction.setTransactionState(TransactionState.ACTIVE.getValue());
        transactionRepository.save(transaction);
        return appointment;

    }

    @Transactional
    public String cancelAppointment(CancelAppointmentRequestDTO requestDTO) {
        Appointment appointment = appointmentRepository.findById(requestDTO.getAppointmentId()).orElse(new Appointment());
        validateCancellation(appointment.getAppointmentId(), appointment.getPatientId(), requestDTO.getPatientId(), appointment.getStartDate(), appointment.getState());
        Double cancellationFee = calcCancellationFee(appointment.getFee(), appointment.getStartDate());
        appointment.setState(AppointmentState.CANCELLED.getValue());
        appointmentRepository.save(appointment);
        applyCancellationTransaction(appointment, cancellationFee);
        if(cancellationFee.equals(0.0)){
            return "Your appointment is cancelled with no cancellation fee charged.";
        }
        return "Your appointment is cancelled. " + cancellationFee + "$ cancellation fee is charged. Remaining " +
                "amount will be transferred to your bank account.";
    }

    private void validateDates(Timestamp start, Timestamp end) throws ValidationException {
        LocalDate startDate = start.toLocalDateTime().toLocalDate();
        LocalTime startTime = start.toLocalDateTime().toLocalTime();
        LocalDate endDate = end.toLocalDateTime().toLocalDate();
        LocalTime endTime = end.toLocalDateTime().toLocalTime();


        if(!startDate.isEqual(endDate) || startTime.isAfter(endTime)){
            // if there is max/min time for single appointment, validation can be added up here.
            throw new ValidationException("bad dates");
        }
    }



    private void isAppointmentAvailable(Long doctorId, Long patientId, Timestamp start, Timestamp end) throws BusinessException {

        validateDates(start, end);
        List<Appointment> conflictingDrAppointments = appointmentRepository.getConflictingDrAppointments(doctorId, start, end);
        if(!Objects.isNull(conflictingDrAppointments) && conflictingDrAppointments.size()==0){
            List<Appointment> conflictingPatientAppointments = appointmentRepository.getConflictingPtAppointments(patientId, start, end);
            if(!Objects.isNull(conflictingPatientAppointments) && conflictingPatientAppointments.size() == 0){
                return;
            }else throw new BusinessException("patient has other conflicting appointment");
        }else{
            throw new BusinessException("doctor has other conflicting appointment");
        }
    }

    private void validateCancellation(Long appointmentId, Long patientId, Long requestPatientId, Timestamp startDate, String appointmentState){

        if(!patientId.equals(requestPatientId)){
            throw new ValidationException("Invalid Appointment");
        }else if(appointmentState.equals(AppointmentState.CANCELLED.getValue())){
            throw new BusinessException("The appointment is already cancelled");
        }
        else if(startDate.before(new Timestamp(new Date().getTime()))){
            throw new BusinessException( "This appointment is outdated. Can't be cancelled.");
        }

    }


    private Double calcCancellationFee(Double appointmentFee, Timestamp startDate){
        //I assume given example rule is valid.
        // an hour before the appointment or less, the penalty is 25% of the price,
        // otherwise there is no cancellation fee
        Timestamp now = new Timestamp(new Date().getTime());
        if (startDate.getTime() - now.getTime() > 3600000) {
            return 0.0;
        }else{
            return appointmentFee*0.25;
        }


    }

    private void applyCancellationTransaction(Appointment appointment, Double fee){

        Transaction saveTransaction = transactionRepository.getTransactionByAppointmentIdAndTransactionType(appointment.getAppointmentId(), TransactionType.SAVE_APPOINTMENT.getValue());
        saveTransaction.setTransactionState(TransactionState.RETURNED.getValue());
        transactionRepository.save(saveTransaction);

        if(fee>0.0){
            Transaction cancelTransaction = new Transaction();
            cancelTransaction.setAppointmentId(appointment.getAppointmentId());
            cancelTransaction.setTransactionDate(new Timestamp(new Date().getTime()));
            cancelTransaction.setPrice(fee);
            cancelTransaction.setTransactionType(TransactionType.CANCEL_APPOINTMENT.getValue());
            cancelTransaction.setTransactionState(TransactionState.ACTIVE.getValue());
            transactionRepository.save(cancelTransaction);
        }

    }

}
