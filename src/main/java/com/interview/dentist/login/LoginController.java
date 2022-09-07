package com.interview.dentist.login;

import com.interview.dentist.exceptions.IncorrectPassword;
import com.interview.dentist.exceptions.PatientNotFound;
import com.interview.dentist.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/patient")
    public Patient patientLogin(@RequestBody LoginDto dto) throws PatientNotFound {
        return loginService.patientLogin(dto);
    }

    @PostMapping("/dentist")
    public boolean dentistLogin(@RequestBody LoginDentistDto dto) throws IncorrectPassword {
        System.out.println(dto.getPassword());
        return loginService.dentistLogin(dto);
    }
}
