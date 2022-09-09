package com.interview.dentist.login;

public class LoginResponse {
    private Long id;
    private String role;
    private String jwt;

    public LoginResponse() {
    }

    public LoginResponse(Long id, String role, String jwt) {
        this.id = id;
        this.role = role;
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

