package com.interview.dentist.configuration;

import com.interview.dentist.appointment.Appointment;
import com.interview.dentist.appointment.AppointmentRepository;
import com.interview.dentist.user.AppUser;
import com.interview.dentist.user.AppUserRepository;
import com.interview.dentist.role.Role;
import com.interview.dentist.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;

@Configuration
public class Config {
    AppUserRepository appUserRepository;
    AppointmentRepository appointmentRepository;
    RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public Config() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner configureTestData(AppointmentRepository appointmentRepository, AppUserRepository appUserRepository, RoleRepository roleRepository){
        this.appointmentRepository = appointmentRepository;
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;

        return args -> {
            fillWithTestData();
        };
    }

    private void fillWithTestData() {
        Role role = new Role("patient");
        roleRepository.save(role);

        Role role1 = new Role("dentist");
        roleRepository.save(role1);

        AppUser dentist = new AppUser("dentist", "dentist", "000000000000", "000000000000", "dentist@gmail.com", passwordEncoder.encode("123"));
        dentist.getRoles().add(role1);

        appUserRepository.save(dentist);

        AppUser appUser1 = new AppUser("Petar", "Petrovic", "1305997850154",
                "123456789", "petar@gmail.com", passwordEncoder.encode("123"));

        appUser1.getRoles().add(role);

        appUserRepository.save(appUser1);
        AppUser appUser2 = new AppUser("Milica", "Milic", "1234567891234",
                "987654321", "milica@gmail.com", passwordEncoder.encode("123"));
        appUser2.getRoles().add(role);
        appUserRepository.save(appUser2);

        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 3, 10, 0, 0), 60, "Čišćenje kamenca", appUser1));
        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 2, 15, 0, 0), 30, "Rutinski pregled", appUser1));
        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 3, 11, 0, 0), 60, "Vađenje zuba", appUser2));
        appointmentRepository.save(new Appointment(LocalDateTime.of(2022, Month.OCTOBER, 3, 10, 0, 0), 30, "Rutinski pregled", appUser2));

    }


}
