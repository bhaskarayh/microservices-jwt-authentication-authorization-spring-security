package com.devzees.appointment.controllers;

import com.devzees.appointment.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public String bookAppointment(@RequestParam LocalDate selectedDate, @RequestParam String department) {
        return appointmentService.bookAppointment(selectedDate, department);
    }
}