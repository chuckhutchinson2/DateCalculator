package org.example.datecalculator.controller;

import org.example.datecalculator.model.*;
import org.example.datecalculator.service.DateCalculatorService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

// http://localhost:8080/swagger-ui.html
@Slf4j
@RestController
@RequestMapping("/api/v1/date")
public class DateCalculatorController {
    private DateCalculatorService dateCalculatorService;
    public DateCalculatorController(DateCalculatorService dateCalculatorService) {
        this.dateCalculatorService = dateCalculatorService;
    }

    @PostMapping("/daysbetween")
    ResponseEntity<Long> daysBetween(@RequestBody DaysBetweenRequest daysBetweenRequest) {

        Long daysBetween = dateCalculatorService.daysBetween(daysBetweenRequest);

        return new ResponseEntity<>(daysBetween, HttpStatus.OK);
    }

    @PostMapping("/workingdaysbetween")
    ResponseEntity<Long> workingDaysBetween(@RequestBody DaysBetweenRequest daysBetweenRequest) {

        Long daysBetween = dateCalculatorService.workingDaysBetween(daysBetweenRequest);

        return new ResponseEntity<>(daysBetween, HttpStatus.OK);
    }

    @PostMapping("/calculate")
    ResponseEntity<LocalDate> calculate(@RequestBody CalculateDateRequest calculateDateRequest) {

        LocalDate dateCalculated = dateCalculatorService.calculate(calculateDateRequest);

        return new ResponseEntity<>(dateCalculated, HttpStatus.OK);
    }
}
