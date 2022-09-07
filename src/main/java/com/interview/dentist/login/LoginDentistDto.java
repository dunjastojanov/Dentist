package com.interview.dentist.login;

public class LoginDentistDto {
    private String password;

    public LoginDentistDto(String password) {
        this.password = password;
    }

    public LoginDentistDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
