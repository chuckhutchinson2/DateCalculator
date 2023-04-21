package org.example.calendar.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.datecalculator.config.ThymeLeafConfig;
import org.example.datecalculator.pdf.impl.PdfGeneratorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CalendarServiceImplTest {

    private PdfGeneratorImpl pdfGenerator;
    private CalendarServiceImpl calendarService;

    @Before
    public void before() {
        ThymeLeafConfig thymeLeafConfig = new ThymeLeafConfig();
        ITemplateResolver iTemplateResolver = thymeLeafConfig.thymeleafTemplateResolver();
        pdfGenerator = new PdfGeneratorImpl(thymeLeafConfig.thymeleafTemplateEngine(iTemplateResolver));
        calendarService = new CalendarServiceImpl(pdfGenerator);
    }
    @Test
    public void testGenerateCalendarNature2023() {
        calendarService.setTheme("nature");
        File file = calendarService.createCalendar(2023, "pdf/calendar-2023.pdf");

        log.info("File {}", file.getAbsolutePath());
    }
    @Test
    public void testGenerateCalendarCalifornia2024() {
        calendarService.setTheme("california");
        File file = calendarService.createCalendar(2024, "pdf/calendar-2024.pdf");

        log.info("File {}", file.getAbsolutePath());
    }

    @Test
    public void testGenerateCalendarFrenchImpressionists2025() {
        calendarService.setTheme("french-impressionists");
        File file = calendarService.createCalendar(2025, "pdf/calendar-2025.pdf");

        log.info("File {}", file.getAbsolutePath());
    }

    @Test
    public void testGenerateCalendarArt2026() {
        calendarService.setTheme("art");
        File file = calendarService.createCalendar(2026, "pdf/calendar-2026.pdf");

        log.info("File {}", file.getAbsolutePath());
    }

    @Test
    public void testGenerateCalendars() {
        String[] themes = { "art", "california", "french-impressionists", "nature"};

        for (String theme : themes) {
            for (int year = 2023; year < 2030; year++) {
                calendarService.setTheme(theme);
                String path = String.format("pdf/%d", year);
                new File(path).mkdirs();
                String calendarName = String.format("pdf/%d/calendar-%s-%d.pdf", year, theme, year);
                calendarService.createCalendar(year, calendarName);
            }
        }
    }
}
