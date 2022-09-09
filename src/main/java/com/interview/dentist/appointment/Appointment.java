package com.interview.dentist.appointment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.interview.dentist.user.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Appointment {
    @Id
    @SequenceGenerator(
            name="appointment_sequence",
            sequenceName = "appointment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appointment_sequence"
    )
    private Long id;
    private LocalDateTime startTime;
    private int duration;

    private String type;

    @OneToOne
    private AppUser appUser;


    public Appointment() {
    }

    public Appointment(Long id, LocalDateTime startTime, int duration, String type, AppUser appUser) {
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        this.type = type;
        this.appUser = appUser;
    }

    public Appointment(LocalDateTime startTime, int duration, String type, AppUser appUser) {
        this.startTime = startTime;
        this.duration = duration;
        this.type = type;
        this.appUser = appUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AppUser getPatient() {
        return appUser;
    }

    public void setPatient(AppUser appUser) {
        this.appUser = appUser;
    }
}
