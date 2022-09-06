package com.interview.dentist.login;

import com.interview.dentist.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final PatientService patientService;

    @Autowired
    public LoginService(PatientService patientService) {
        this.patientService = patientService;
    }
}
