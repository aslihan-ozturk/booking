package com.vngrs.booking.controller;

import com.vngrs.booking.model.Doctor;
import com.vngrs.booking.model.requestDto.ChangeRateRequestBodyDTO;
import com.vngrs.booking.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
     DoctorService doctorService;

    @PostMapping(value = "/save")
    public Doctor save(@RequestBody Doctor doctor){
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping("/getAll")
    public List<Doctor> getAll(){
        return doctorService.getAll();
    }

    @PostMapping("/changeRate")
    public Doctor changeRate(@RequestBody ChangeRateRequestBodyDTO changeRateRequestBodyDTO){
        return doctorService.changeRate(changeRateRequestBodyDTO.getDoctorId(), changeRateRequestBodyDTO.getNewRate());
    }
}
