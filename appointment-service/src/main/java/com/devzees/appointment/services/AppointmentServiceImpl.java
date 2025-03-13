package com.devzees.appointment.services;

import com.devzees.appointment.dtos.HealthProvider;
import com.devzees.appointment.models.Appointment;
import com.devzees.appointment.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final HealthProviderClient healthProviderClient;
    private final AppointmentRepository appointmentRepository;

    @Override
    public String bookAppointment(LocalDate date, String hpDepartment) {
        try {
            // Check if request goes through the proxy
            List<HealthProvider> healthProviders = healthProviderClient.getAvailableHealthProviders(date, hpDepartment);

            Appointment appointment = new Appointment();
            appointment.setAppointmentDate(date);
            appointment.setHpDepartment(hpDepartment);
            appointment.setHpName(healthProviders.getFirst().getHpName());

            appointmentRepository.save(appointment);
            return "Your appointment is confirmed.!";

        } catch (HttpClientErrorException ex) {
            // Log the full stack trace and the response details
            log.error("HTTP Error: Status Code: {}, Response Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return "Failed to book appointment: " + ex.getMessage();
        } catch (Exception ex) {
            // Log any other errors that might occur
            log.error("Error occurred while booking appointment", ex);
            return "An error occurred while booking appointment: " + ex.getMessage();
        }
    }
}
