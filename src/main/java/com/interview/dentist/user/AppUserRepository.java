package com.interview.dentist.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("FROM AppUser WHERE jmbg = ?1 AND lbo=?2")
    AppUser getPatientByCredentials(String jmbg, String lbo);

    @Query("FROM AppUser WHERE id = ?1")
    AppUser getPatient(Long patientId);

    AppUser getPatientByEmail(String email);


}
