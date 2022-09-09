package com.interview.dentist.login;

import com.interview.dentist.user.AppUser;
import com.interview.dentist.user.AppUserService;
import com.interview.dentist.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AppUserService appUserService;
    private final UserService userService;

    @Autowired
    public LoginService(AppUserService appUserService, UserService userService) {
        this.appUserService = appUserService;
        this.userService = userService;
    }

    public UserDetails getUser(String username) {
        return userService.loadUserByUsername(username);
    }

    public AppUser getPatient(String username) {
        return appUserService.getUserByEmail(username);
    }
}
