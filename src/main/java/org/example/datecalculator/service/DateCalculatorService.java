package org.example.datecalculator.service;

import org.example.datecalculator.model.*;

import java.time.LocalDate;

public interface DateCalculatorService {

    long workingDaysBetween(DaysBetweenRequest daysBetweenRequest);

    long daysBetween(DaysBetweenRequest daysBetweenRequest);

    LocalDate calculate(CalculateDateRequest calculateDateRequest);
}
