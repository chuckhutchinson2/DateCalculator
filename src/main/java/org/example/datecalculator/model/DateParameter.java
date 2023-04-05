package org.example.datecalculator.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateParameter {
    private String id;
    private LocalDate localDate;
    private Integer years;
    private Integer months;
    private Integer days;
}
