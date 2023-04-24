package org.example.calendar.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.calendar.model.Theme;
import org.example.calendar.service.CalendarService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Slf4j
@RestController
@RequestMapping("/api/v1/calendar")
public class CalendarController {

    private CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(value = "/get/{year}/{theme}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<StreamingResponseBody> download(@PathVariable Integer year, @PathVariable Theme theme) {

        log.info("Generating calendar for %s %d", theme.getPath(), year);
        calendarService.setTheme(theme.getPath());
        StreamingResponseBody responseBody = outputStream -> {
            calendarService.createCalendar(year, outputStream);
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=calendar-%d.pdf", year))
                .contentType(MediaType.APPLICATION_PDF)
                .body(responseBody);
    }
}
