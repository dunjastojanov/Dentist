package com.interview.dentist.patient;

import com.interview.dentist.exceptions.PatientNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getPatientByCredentials(String jmbg, String lbo) throws PatientNotFound {
        Patient patient = patientRepository.getPatientByCredentials(jmbg, lbo);
        if (patient == null) {
            throw new PatientNotFound();
        }
        return patient;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long patientId) {
        return patientRepository.getPatient(patientId);
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }
}
