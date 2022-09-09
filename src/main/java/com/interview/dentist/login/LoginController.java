package com.interview.dentist.login;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.interview.dentist.user.AppUser;
import com.interview.dentist.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(LoginService loginService, AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/user")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse loginResponse = new LoginResponse();

        AppUser appUser = loginService.getPatient(dto.getEmail());
        loginResponse.setId(appUser.getId());
        Role role = (Role) appUser.getRoles().toArray()[0];
        loginResponse.setRole(role.getName());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withSubject(dto.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 180 * 60 * 1000))
                .sign(algorithm);

        loginResponse.setJwt(token);

        if (loginResponse.getRole() != null) {
            return ResponseEntity.ok(loginResponse);
        }
        else return ResponseEntity.ok(new LoginResponse(null, null, null));
    }
}
