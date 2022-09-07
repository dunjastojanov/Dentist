package com.interview.dentist.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("FROM Patient WHERE jmbg = ?1 AND lbo=?2")
    Patient getPatientByCredentials(String jmbg, String lbo);

    @Query("FROM Patient WHERE id = ?1")
    Patient getPatient(Long patientId);
}
