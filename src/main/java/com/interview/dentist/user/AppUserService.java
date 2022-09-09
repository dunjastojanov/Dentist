package com.interview.dentist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser getById(Long patientId) {
        return appUserRepository.getPatient(patientId);
    }

    public void addUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public AppUser getUserByEmail(String username) {
        return appUserRepository.getPatientByEmail(username);
    }
}
