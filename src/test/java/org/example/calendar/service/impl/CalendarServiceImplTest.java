package org.example.calendar.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.calendar.model.Month;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CalendarServiceImplTest {

    @Test
    public void testGenerateCalendar() {

        CalendarServiceImpl calendarService = new CalendarServiceImpl();

        List<Month> months = calendarService.create(2023);

        log.info("months {}", months);

        for(Month month : months) {
            log.info ("Month: {} has {} weeks", month.getMonthName(), month.getWeeks().size());
        }
    }
}
