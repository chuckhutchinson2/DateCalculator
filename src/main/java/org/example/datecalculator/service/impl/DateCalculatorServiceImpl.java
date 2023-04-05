package org.example.datecalculator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.datecalculator.model.DateParameter;
import org.example.datecalculator.service.DateCalculatorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@Slf4j
public class DateCalculatorServiceImpl implements DateCalculatorService {


    @Override
    public LocalDate calculate(DateParameter dateParameter) {
        log.info("getDateParameters(String {}) ", dateParameter);

        return LocalDate.now();
    }
}
