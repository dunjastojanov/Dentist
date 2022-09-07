package com.interview.dentist.login;

import com.interview.dentist.exceptions.IncorrectPassword;
import com.interview.dentist.exceptions.PatientNotFound;
import com.interview.dentist.patient.Patient;
import com.interview.dentist.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {
    private final PatientService patientService;

    @Autowired
    public LoginService(PatientService patientService) {
        this.patientService = patientService;
    }

    public Patient patientLogin(LoginDto dto) throws PatientNotFound {
        return patientService.getPatientByCredentials(dto.getJmbg(), dto.getLbo());
    }

    public boolean dentistLogin(LoginDentistDto dto) throws IncorrectPassword{
        if (Objects.equals(dto.getPassword(), "123")) {
            return true;
        } else throw new IncorrectPassword();
    }
}
