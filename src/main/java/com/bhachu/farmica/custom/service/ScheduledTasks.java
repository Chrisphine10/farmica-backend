package com.bhachu.farmica.custom.service;

import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ReportService reportService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Run every 24 hours
    public void executeTask() {
        // Your task logic goes here
        System.err.println("Task executed at: " + System.currentTimeMillis());
        // Get the current date and time in the system's default time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now();

        // Get the start of the day
        ZonedDateTime startOfDay = currentDateTime.toLocalDate().atStartOfDay(currentDateTime.getZone());

        // Get the end of the day
        ZonedDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        reportService.createNewReport(startOfDay, endOfDay);
        reportService.createStyleReport(startOfDay, endOfDay);
    }
}
