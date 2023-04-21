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
    public void testGenerateCalendar2023() {
        calendarService.setTheme("nature");
        File file = calendarService.createCalendar(2023, "pdf/calendar-2023.pdf");

        log.info("File {}", file.getAbsolutePath());
    }
    @Test
    public void testGenerateCalendar2024() {
        calendarService.setTheme("california");
        File file = calendarService.createCalendar(2024, "pdf/calendar-2024.pdf");

        log.info("File {}", file.getAbsolutePath());
    }
}
