package com.vngrs.booking.model.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeRateRequestBodyDTO {

    @NotNull(message = "doctorId required")
    private Long doctorId;

    @NotNull(message = "newRate required")
    private Double newRate;
}
