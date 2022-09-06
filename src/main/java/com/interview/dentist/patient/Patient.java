package com.interview.dentist.patient;

import javax.persistence.*;

@Entity
@Table
public class Patient {
    @Id
    @SequenceGenerator(
            name="patient_sequence",
            sequenceName = "patient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String lbo;

    public Patient() {
    }

    public Patient(Long id, String firstName, String lastName, String jmbg, String lbo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.lbo = lbo;
    }

    public Patient(String firstName, String lastName, String jmbg, String lbo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.lbo = lbo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
