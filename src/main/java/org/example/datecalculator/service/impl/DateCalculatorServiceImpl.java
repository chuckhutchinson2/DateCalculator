package org.example.datecalculator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.datecalculator.model.CalculateDateRequest;
import org.example.datecalculator.model.DaysBetweenRequest;
import org.example.datecalculator.service.DateCalculatorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


// https://www.javatpoint.com/java-localdate
@Service
@Slf4j
public class DateCalculatorServiceImpl implements DateCalculatorService {

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
