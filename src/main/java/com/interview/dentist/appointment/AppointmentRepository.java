package com.interview.dentist.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findAppointmentById(Long id);
    @Query("FROM Appointment WHERE patient.id = ?1")
    List<Appointment> findAppointmentsForPatient(Long id);
}
