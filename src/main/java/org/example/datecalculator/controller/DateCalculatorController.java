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
@RequestMapping("/api/v1/calculate")
public class DateCalculatorController {
    private DateCalculatorService dateCalculatorService;
    public DateCalculatorController(DateCalculatorService dateCalculatorService) {
        this.dateCalculatorService = dateCalculatorService;
    }

    @PostMapping("/dateparameters")
    ResponseEntity<LocalDate> getDateParameters(@RequestBody DateParameter dateParameter) {

        LocalDate dateCalculated = dateCalculatorService.calculate(dateParameter);

        return new ResponseEntity<>(dateCalculated, HttpStatus.OK);
    }
}
