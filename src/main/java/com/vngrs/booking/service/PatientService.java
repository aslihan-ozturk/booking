package com.vngrs.booking.service;

import com.vngrs.booking.enums.AppointmentState;
import com.vngrs.booking.exception.ValidationException;
import com.vngrs.booking.model.Appointment;
import com.vngrs.booking.model.Doctor;
import com.vngrs.booking.model.Patient;
import com.vngrs.booking.model.responseDTO.PatientAppointmentResponseDTO;
import com.vngrs.booking.repository.AppointmentRepository;
import com.vngrs.booking.repository.DoctorRepository;
import com.vngrs.booking.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PatientService {
   @Autowired
   PatientRepository patientRepository;
    @Autowired
     AppointmentRepository appointmentRepository;
    @Autowired
     DoctorRepository doctorRepository;


    public List<PatientAppointmentResponseDTO> getAppointmentsByPatientId(Long patientId){
        List<PatientAppointmentResponseDTO> result = new ArrayList<>();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ValidationException("Invalid patientId"));

        List<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId);
        appointments.forEach( appointment -> {
            Doctor doctor = doctorRepository.findById(appointment.getDoctorId()).orElse(new Doctor());
            result.add(PatientAppointmentResponseDTO.builder().appointmentId(appointment.getAppointmentId()).startDate(appointment.getStartDate())
                    .endDate(appointment.getEndDate()).patient(patient).doctor(doctor).state(getState(appointment)).build());
        });

        return result;
    }

    public Patient savePatient(Patient patient){
        if(Objects.isNull(patient.getPatientName()) || patient.getPatientName().equals("")){
            throw new ValidationException("patientName required");
        }
        return patientRepository.save(patient);
    }

    private String getState(Appointment appointment){
        if(appointment.getState().equals(AppointmentState.CANCELLED.getValue())){
            return "Cancelled appointment";
        }else if(appointment.getState().equals(AppointmentState.ACTIVE.getValue())){
            Timestamp now = new Timestamp(new Date().getTime());
            if(now.after(appointment.getStartDate())){
                return "Undue appointment";
            }else return "Active appointment";
        }

        return "NA";

    }
}
