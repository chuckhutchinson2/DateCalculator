package org.example.datecalculator.service.impl;


import org.example.datecalculator.model.CalculateDateRequest;
import org.example.datecalculator.model.DaysBetweenRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class DateCalculatorServiceImplTest {


    @Autowired
    @InjectMocks
    private  DateCalculatorServiceImpl dateCalculatorService;

    @Test
    public void testCalculateDays() {
        CalculateDateRequest calculateDateRequest = new CalculateDateRequest();
        calculateDateRequest.setLocalDate(LocalDate.parse("2024-04-01"));
        calculateDateRequest.setDays(1);

        LocalDate expectedDate = LocalDate.parse("2024-04-02");

        assertEquals(expectedDate, dateCalculatorService.calculate(calculateDateRequest));
    }

    @Test
    public void testCalculateMonths() {
        CalculateDateRequest calculateDateRequest = new CalculateDateRequest();
        calculateDateRequest.setLocalDate(LocalDate.parse("2024-04-01"));
        calculateDateRequest.setMonths(1);

        LocalDate expectedDate = LocalDate.parse("2024-05-01");

        assertEquals(expectedDate, dateCalculatorService.calculate(calculateDateRequest));
    }

    @Test
    public void testCalculateYears() {
        CalculateDateRequest calculateDateRequest = new CalculateDateRequest();
        calculateDateRequest.setLocalDate(LocalDate.parse("2024-04-01"));
        calculateDateRequest.setYears(1);

        LocalDate expectedDate = LocalDate.parse("2025-04-01");

        assertEquals(expectedDate, dateCalculatorService.calculate(calculateDateRequest));
    }

    @Test
    public void testDaysBetween() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2024-04-01"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2024-05-05"));

        assertEquals(34, dateCalculatorService.daysBetween(daysBetweenRequest));
    }

    @Test
    public void testWorkingDaysBetweenWhenStartDateIsOnAWeekday() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2023-04-03"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2023-05-05"));

        assertEquals(24, dateCalculatorService.workingDaysBetween(daysBetweenRequest));
    }

    @Test
    public void testWorkingDaysBetweenWhenStartDateIsOnAWeekdayAndEndDateIsASaturday() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2023-04-03"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2023-05-06"));

        assertEquals(24, dateCalculatorService.workingDaysBetween(daysBetweenRequest));
    }

    @Test
    public void testWorkingDaysBetweenWhenStartDateIsOnAWeekdayAndEndDateIsASunday() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2023-04-03"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2023-05-07"));

        assertEquals(24, dateCalculatorService.workingDaysBetween(daysBetweenRequest));
    }
    @Test
    public void testWorkingDaysBetweenWhenStartDateIsOnAWeekendSaturday() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2023-04-01"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2023-05-05"));

        assertEquals(25, dateCalculatorService.workingDaysBetween(daysBetweenRequest));
    }

    @Test
    public void testWorkingDaysBetweenWhenStartDateIsOnAWeekendSunday() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2023-04-02"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2023-05-05"));

        assertEquals(25, dateCalculatorService.workingDaysBetween(daysBetweenRequest));
    }

    @Test
    public void testWorkingDaysBetweenWhenStartDateAndEndDateWithinAWeek() {
        DaysBetweenRequest daysBetweenRequest = new DaysBetweenRequest();
        daysBetweenRequest.setStartDate(LocalDate.parse("2023-04-03"));
        daysBetweenRequest.setEndDate(LocalDate.parse("2023-04-06"));

        assertEquals(3, dateCalculatorService.workingDaysBetween(daysBetweenRequest));
    }
}
