package com.interview.dentist.configuration;

import com.interview.dentist.appointment.Appointment;
import com.interview.dentist.appointment.AppointmentRepository;
import com.interview.dentist.patient.Patient;
import com.interview.dentist.patient.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.time.Month;

@Configuration
public class Config {

    PatientRepository patientRepository;
    AppointmentRepository appointmentRepository;

    @Bean
    CommandLineRunner configureTestData(AppointmentRepository appointmentRepository, PatientRepository patientRepository){
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;

        return args -> {
            fillWithTestData();
        };
    }

    private void fillWithTestData() {

        Patient patient1 = new Patient("Petar", "Petrovic", "1305997850154",
                "123456789", "dunjastojanov2+petar.petorvic@gmail.com");
        patientRepository.save(patient1);
        Patient patient2 = new Patient("Milica", "Milic", "1234567891234",
                "987654321", "dunjastojanov2+milica.milic@gmail.com");
        patientRepository.save(patient2);

        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 3, 10, 0, 0), 60, "Čišćenje kamenca", patient1));
        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 2, 15, 0, 0), 30, "Rutinski pregled", patient1));
        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 3, 11, 0, 0), 60, "Vađenje zuba", patient2));
        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 3, 10, 0, 0), 30, "Rutinski pregled", patient2));

    }


}
