package com.vngrs.booking.model.responseDTO;


import com.vngrs.booking.model.Doctor;
import com.vngrs.booking.model.Patient;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class PatientAppointmentResponseDTO {

    private Long appointmentId;
    private String state;
    private Timestamp startDate;
    private Timestamp endDate;
    private Doctor doctor;
    private Patient patient;



}
