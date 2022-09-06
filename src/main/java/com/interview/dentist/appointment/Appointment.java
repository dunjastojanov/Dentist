package com.interview.dentist.appointment;

import com.interview.dentist.patient.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;


}
