package com.vngrs.booking.repository;

import com.vngrs.booking.model.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    @Query(value = "select hourly_rate from doctor where doctor_id = ?", nativeQuery = true)
    Double getHourlyRate(Long doctorId);

}
