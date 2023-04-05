package org.example.datecalculator.service;

import org.example.datecalculator.model.*;

import java.time.LocalDate;

public interface DateCalculatorService {
    LocalDate calculate(DateParameter dateParameter);
}
