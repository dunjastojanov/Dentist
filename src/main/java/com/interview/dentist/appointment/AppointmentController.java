package com.interview.dentist.appointment;

import com.interview.dentist.exceptions.AppointmentNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public List<Appointment> getAppointmentsForPatient(@PathVariable Long id) {
        return appointmentService.getAppointmentsForPatient(id);
    }

    @PostMapping("/add")
    public Long addAppointment(@RequestBody AppointmentDto dto)  throws AppointmentNotAvailable {
        return appointmentService.addAppointment(dto);
    }

    @PostMapping("/addWithNewPatient")
    public Long addAppointmentWithNewPatient(@RequestBody NewPatientAppointmentDto dto)  throws AppointmentNotAvailable {
        return appointmentService.addAppointmentWithNewPatient(dto);
    }

    @PostMapping("/cancel/{id}")
    public void cancelAppointment(@PathVariable Long id) throws AppointmentNotAvailable{
        appointmentService.cancelAppointment(id);
    }
}
