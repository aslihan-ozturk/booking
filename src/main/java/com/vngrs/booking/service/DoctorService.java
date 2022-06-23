package com.vngrs.booking.service;

import com.vngrs.booking.exception.ValidationException;
import com.vngrs.booking.model.Doctor;
import com.vngrs.booking.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
public class DoctorService {

    @Autowired
     DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAll(){
        return (List<Doctor>) doctorRepository.findAll();
    }

    public Doctor changeRate(Long doctorId, Double newRate){
        if(Objects.isNull(newRate) && newRate == 0.0){
            throw new ValidationException("invalid new rate");
        }
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ValidationException( "Invalid doctor id"));
        doctor.setHourlyRate(newRate);
        return doctorRepository.save(doctor);
    }
}
