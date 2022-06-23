package com.vngrs.booking.model.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class GetAppointmentFeeRequestDTO {

    @NotNull(message = "doctorId required")
    private Long doctorId;

    @NotNull(message = "patientId required")
    private Long patientId;

    @NotNull(message = "startDate required")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Istanbul")
    private Timestamp startDate;

    @NotNull(message = "endDate required")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Istanbul")
    private Timestamp endDate;

}
