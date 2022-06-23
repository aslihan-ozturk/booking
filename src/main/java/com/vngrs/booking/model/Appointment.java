package com.vngrs.booking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @NotNull(message = "patientId required")
    private Long patientId;
    @NotNull(message = "doctorId required")
    private Long doctorId;
    @NotNull(message = "fee required")
    private Double fee;
    private String state; // A for active appointment, C for cancelled
    @NotNull(message = "startDate required")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Istanbul")
    private Timestamp startDate;
    @NotNull(message = "endDate required")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Istanbul")
    private Timestamp endDate;


}
