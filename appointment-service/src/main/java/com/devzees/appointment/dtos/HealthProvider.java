package com.devzees.appointment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthProvider {

    private String id;
    private String hpName;
    private String hpDepartment;
    private Boolean isDailyAvailable;
    private List<Long> daysAvailable;
    private LocalTime dutyStartTime;
    private LocalTime dutyEndTime;

}