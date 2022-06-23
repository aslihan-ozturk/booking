package com.vngrs.booking.controller;


import com.vngrs.booking.model.Patient;
import com.vngrs.booking.model.responseDTO.PatientAppointmentResponseDTO;
import com.vngrs.booking.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
     PatientService patientService;

    @GetMapping("/appointments/{id}")
    public List<PatientAppointmentResponseDTO> getAppointmentsByPatientId(@PathVariable Long id){
        return patientService.getAppointmentsByPatientId(id);
    }

    @PostMapping("/save")
    public Patient savePatient (@RequestBody  @Valid Patient patient){
        return patientService.savePatient(patient);
    }
}
