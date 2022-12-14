package com.interview.dentist.appointment;

import com.interview.dentist.email.EmailSenderService;
import com.interview.dentist.exceptions.AppointmentNotAvailable;
import com.interview.dentist.user.AppUser;
import com.interview.dentist.user.AppUserService;
import com.interview.dentist.role.Role;
import com.interview.dentist.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppUserService appUserService;
    private final EmailSenderService emailSenderService;

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, AppUserService appUserService, EmailSenderService emailSenderService, RoleRepository roleRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appUserService = appUserService;
        this.emailSenderService = emailSenderService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsForPatient(Long id) {
        return appointmentRepository.findAppointmentsForPatient(id);
    }

    public Long addAppointment(AppointmentDto dto) throws AppointmentNotAvailable {
        Appointment appointment = new Appointment(LocalDateTime.of(dto.getYear(), Month.of(dto.getMonth()), dto.getDay(), dto.getHour(), dto.getMinute(), 0), dto.getDuration(), dto.getType(), appUserService.getById(dto.getPatientId())

        );
        if (isAppointmentAvailable(appointment)) {
            appointmentRepository.save(appointment);

            sendConformationEmail(appointment);

            return appointment.getId();
        } else {
            throw new AppointmentNotAvailable();
        }
    }

    private void sendConformationEmail(Appointment appointment) {
        String date = appointment.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = appointment.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        String body = "Po??tovani " + appointment.getPatient().getFirstName() + ",\n" + "Obave??tavamo Vas da je zakazan termin za " + appointment.getType() + " dana " + date + " sa po??etkom u " + time + ".";

        emailSenderService.sendEmail(appointment.getPatient().getEmail(), "Potvrda zakazivanja termina u zubarskoj ordinaciji", body);

        body = "Po??tovani,\n" + "Obave??tavamo Vas da je zakazan termin za " + appointment.getType() + " dana " + date + " sa po??etkom u " + time + ".\nIme pacijenta je " + appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName() + ".";

        emailSenderService.sendEmail("dunjastojanov2@gmail.com", "Potvrda zakazivanja termina u zubarskoj ordinaciji", body);
    }

    private void sendCancellationEmail(Appointment appointment) {
        String date = appointment.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = appointment.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        String body = "Po??tovani " + appointment.getPatient().getFirstName() + ",\n" + "Obave??tavamo Vas da je otkazan termin za " + appointment.getType() + " dana " + date + " sa po??etkom u " + time + ".";

        emailSenderService.sendEmail(appointment.getPatient().getEmail(), "Potvrda otkazivanja termina u zubarskoj ordinaciji", body);

        body = "Po??tovani,\n" + "Obave??tavamo Vas da je zakazan termin za " + appointment.getType() + " dana " + date + " sa po??etkom u " + time + ".\nIme pacijenta je " + appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName() + ".";

        emailSenderService.sendEmail("dunjastojanov2@gmail.com", "Potvrda otkazivanja termina u zubarskoj ordinaciji", body);
    }

    public Long addAppointmentWithNewPatient(NewPatientAppointmentDto dto) {
        AppUser appUser = new AppUser(dto.getFirstName(), dto.getLastName(), dto.getJmbg(), dto.getLbo(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        Role role = new Role("patient");
        roleRepository.save(role);
        appUser.getRoles().add(role);


        appUserService.addUser(appUser);

        Appointment appointment = new Appointment(LocalDateTime.of(dto.getYear(), Month.of(dto.getMonth()), dto.getDay(), dto.getHour(), dto.getMinute(), 0), dto.getDuration(), dto.getType(), appUser);
        if (isAppointmentAvailable(appointment)) {
            appointmentRepository.save(appointment);
            sendConformationEmail(appointment);
            return appointment.getId();
        } else {
            throw new AppointmentNotAvailable();
        }
    }

    private boolean isAppointmentAvailable(Appointment appointment) {

        LocalDateTime openingTime = LocalDateTime.of(appointment.getStartTime().getYear(), appointment.getStartTime().getMonth(), appointment.getStartTime().getDayOfMonth(), 9, 0, 0);

        LocalDateTime closingTime = LocalDateTime.of(appointment.getStartTime().getYear(), appointment.getStartTime().getMonth(), appointment.getStartTime().getDayOfMonth(), 17, 0, 0);


        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime endTime = appointment.getStartTime().plusMinutes(appointment.getDuration());

        if (startTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || startTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return false;
        }
        if (startTime.isBefore(openingTime) || endTime.isAfter(closingTime)) {
            return false;
        }

        List<Appointment> appointments = appointmentRepository.findAll();
        appointments = appointments.stream().filter(a -> a.getStartTime().getYear() == appointment.getStartTime().getYear()).toList();
        appointments = appointments.stream().filter(a -> a.getStartTime().getMonth() == appointment.getStartTime().getMonth()).toList();
        appointments = appointments.stream().filter(a -> a.getStartTime().getDayOfMonth() == appointment.getStartTime().getDayOfMonth()).toList();


        for (Appointment a : appointments) {
            LocalDateTime s = a.getStartTime();
            LocalDateTime e = a.getStartTime().plusMinutes(a.getDuration());
            if (startTime.isAfter(s) && startTime.isBefore(e) || endTime.isAfter(s) && endTime.isBefore(e) || startTime.isEqual(s) || endTime.isEqual(e))
                return false;
        }
        return true;
    }

    public void cancelAppointment(Long id) throws AppointmentNotAvailable {
        Appointment appointment = appointmentRepository.findAppointmentById(id);

        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime lastCancellationTime = LocalDateTime.now();

        lastCancellationTime = lastCancellationTime.plusHours(24);

        if (startTime.isAfter(lastCancellationTime)) {
            appointmentRepository.deleteById(id);
            sendCancellationEmail(appointment);
        } else {
            throw new AppointmentNotAvailable();
        }


    }
}
