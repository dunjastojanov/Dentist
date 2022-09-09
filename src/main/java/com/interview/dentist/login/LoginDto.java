package com.interview.dentist.login;

public class LoginDto {
    private String email;
    private String password;

    public LoginDto() {
    }

    public LoginDto(String jmbg, String lbo) {
        this.email = jmbg;
        this.password = lbo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
