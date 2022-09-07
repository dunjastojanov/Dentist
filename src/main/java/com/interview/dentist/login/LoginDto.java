package com.interview.dentist.login;

public class LoginDto {
    private String jmbg;
    private String lbo;

    public LoginDto() {
    }

    public LoginDto(String jmbg, String lbo) {
        this.jmbg = jmbg;
        this.lbo = lbo;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getLbo() {
        return lbo;
    }

    public void setLbo(String lbo) {
        this.lbo = lbo;
    }
}
