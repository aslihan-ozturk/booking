package com.vngrs.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    @NotNull(message = "horulyrate required")
    private Double hourlyRate;
    @NotBlank(message = "doctorName required")
    private String doctorName;
    @NotBlank(message = "branch required")
    private String branch;
}
