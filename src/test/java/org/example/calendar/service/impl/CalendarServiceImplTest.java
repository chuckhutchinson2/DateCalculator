package org.example.calendar.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.calendar.model.Month;
import org.example.datecalculator.config.ThymeLeafConfig;
import org.example.datecalculator.pdf.impl.PdfGeneratorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        CalendarServiceImpl calendarService = new CalendarServiceImpl();

        List<Month> months = calendarService.create(2023);

        log.info("months {}", months);

        for(Month month : months) {
            String imageName = String.format("images/%s.JPG", month.getMonthName());
            log.info ("Month: {} has {} weeks {} ", month.getMonthName(), month.getWeeks().size(), imageName);
            month.setImage(imageName);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("months", months);
        File file = pdfGenerator.generatePDF("pdf/calendar.pdf", "calendar.html", model);

        log.info("File {}", file.getAbsolutePath());
    }
}
