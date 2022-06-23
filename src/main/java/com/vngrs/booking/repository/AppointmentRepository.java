package com.vngrs.booking.repository;

import com.vngrs.booking.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;


@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {

    @Query(value = "select * from appointment a where a.patient_id = ?1 ", nativeQuery = true)
    List<Appointment> findAllByPatientId(Long patientId);

    @Query(value = "SELECT * FROM  appointment A WHERE A.doctor_id = ?1  AND" +
            "((A.start_date < ?3 AND A.end_date > ?3) OR " +
            "(A.start_date < ?2 AND A.end_date> ?2) OR " +
            "(A.start_date >= ?2 AND A.end_date <= ?3))"+
            "AND A.state='A' ", nativeQuery = true)
    List<Appointment> getConflictingDrAppointments(Long doctorId, Timestamp startDate, Timestamp endDate);

    @Query(value = "SELECT * FROM  appointment A WHERE A.patient_id = ?1  AND" +
            "((A.start_date < ?3 AND A.end_date > ?3) OR " +
            "(A.start_date < ?2 AND A.end_date> ?2) OR " +
            "(A.start_date >= ?2 AND A.end_date <= ?3))"+
            "AND A.state='A' ", nativeQuery = true)
    List<Appointment> getConflictingPtAppointments(Long patientId, Timestamp startDate, Timestamp endDate);
}


