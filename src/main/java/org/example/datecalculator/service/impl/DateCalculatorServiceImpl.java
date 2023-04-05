package org.example.datecalculator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.datecalculator.model.CalculateDateRequest;
import org.example.datecalculator.model.DaysBetweenRequest;
import org.example.datecalculator.service.DateCalculatorService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


// https://www.javatpoint.com/java-localdate

// https://www.omnicalculator.com/everyday-life/working-days#how-many-working-days-between-two-dates
// https://www.codeproject.com/Articles/10641/Optimized-Calculation-Algorithm-for-Business-Days
// https://stackoverflow.com/questions/43541944/fastest-algorithm-to-calculate-the-number-of-business-days-between-two-dates


@Service
@Slf4j
public class DateCalculatorServiceImpl implements DateCalculatorService {

    public static List<DayOfWeek> WEEKEND_DAYS = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    @Override
    public long workingDaysBetween(DaysBetweenRequest daysBetweenRequest) {
/*
https://stackoverflow.com/questions/43541944/fastest-algorithm-to-calculate-the-number-of-business-days-between-two-dates

        def businessDaysBetween(startDate: DateTime, endDate: DateTime): Int = {
                val workDayStart = getPreviousWorkDay(startDate)
                val workDayEnd = getPreviousWorkDay(endDate)

                val daysBetween = Days.daysBetween(workDayStart, workDayEnd).getDays
                val weekendDaysBetween = daysBetween / 7 * 2
                val additionalWeekend = if(workDayStart.getDayOfWeek > workDayEnd.getDayOfWeek) 2 else 0

        daysBetween - weekendDaysBetween - additionalWeekend
  */
        LocalDate startDate = Optional.ofNullable(daysBetweenRequest.getStartDate()).orElse(LocalDate.now());
        LocalDate endDate = Optional.ofNullable(daysBetweenRequest.getEndDate()).orElse(LocalDate.now());

        LocalDate workStartDate = getPreviousWorkDate(startDate);
        LocalDate workEndDate = getPreviousWorkDate(endDate);

        long daysBetween = ChronoUnit.DAYS.between(workStartDate, workEndDate);

        long weekendDaysBetween = daysBetween / 7 * 2;

        long additionalWeekend = workStartDate.getDayOfWeek().getValue() > workEndDate.getDayOfWeek().getValue() ? 2 : 0;

        log.info("daysBetween {}", daysBetween);
        log.info("weekendDaysBetween {}", weekendDaysBetween);
        log.info("additionalWeekend {}", additionalWeekend);


        return daysBetween - weekendDaysBetween - additionalWeekend;
    }

    LocalDate getPreviousWorkDate(LocalDate localDate) {

        while(isWeekendDate(localDate)) {
            localDate = localDate.minusDays(1);
        }

        return localDate;
    }

    Boolean isWeekendDate(LocalDate localDate) {
        return WEEKEND_DAYS.contains(localDate.getDayOfWeek());
    }

    @Override
    public long daysBetween(DaysBetweenRequest daysBetweenRequest) {
        LocalDate startDate = Optional.ofNullable(daysBetweenRequest.getStartDate()).orElse(LocalDate.now());
        LocalDate endDate = Optional.ofNullable(daysBetweenRequest.getEndDate()).orElse(LocalDate.now());

        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public LocalDate calculate(CalculateDateRequest calculateDateRequest) {
        log.info("calculate(DateParameter {}) ", calculateDateRequest);

        LocalDate localDate = Optional.ofNullable(calculateDateRequest.getLocalDate()).orElse(LocalDate.now());

        return localDate.plusYears(Optional.ofNullable(calculateDateRequest.getYears()).orElse(0))
                 .plusMonths(Optional.ofNullable(calculateDateRequest.getMonths()).orElse(0))
                 .plusDays(Optional.ofNullable(calculateDateRequest.getDays()).orElse(0));
    }
}
