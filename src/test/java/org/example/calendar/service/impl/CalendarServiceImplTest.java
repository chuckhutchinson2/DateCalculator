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

    @Before
    public void before() {
        ThymeLeafConfig thymeLeafConfig = new ThymeLeafConfig();
        ITemplateResolver iTemplateResolver = thymeLeafConfig.thymeleafTemplateResolver();
        pdfGenerator = new PdfGeneratorImpl(thymeLeafConfig.thymeleafTemplateEngine(iTemplateResolver));
    }
    @Test
    public void testGenerateCalendar() {

        CalendarServiceImpl calendarService = new CalendarServiceImpl(pdfGenerator);

        File file = calendarService.createCalendar(2023, "pdf/calendar-2023.pdf");

        log.info("File {}", file.getAbsolutePath());
    }
}
