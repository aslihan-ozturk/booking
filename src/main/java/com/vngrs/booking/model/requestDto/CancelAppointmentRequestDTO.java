package com.vngrs.booking.model.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelAppointmentRequestDTO {

    @NotNull(message = "patientId required")
    private Long patientId;

    @NotNull(message = "appointmentId required")
    private Long appointmentId;
}
