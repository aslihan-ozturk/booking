package com.vngrs.booking.controller;

import com.vngrs.booking.model.Appointment;
import com.vngrs.booking.model.requestDto.CancelAppointmentRequestDTO;
import com.vngrs.booking.model.requestDto.GetAppointmentFeeRequestDTO;
import com.vngrs.booking.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
     AppointmentService appointmentService;

    @PostMapping("/save")
    public Appointment saveAppointment(@Valid @RequestBody Appointment appointment) throws Exception {
        return appointmentService.saveAppointment(appointment);
    }

    @PostMapping("/calcFee")
    public Double calcAppointmentFee(@Valid @RequestBody GetAppointmentFeeRequestDTO requestDTO) throws Exception {
        return appointmentService.getAppointmentFee(requestDTO);
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestBody CancelAppointmentRequestDTO requestDTO){
        return appointmentService.cancelAppointment(requestDTO);
    }
}
