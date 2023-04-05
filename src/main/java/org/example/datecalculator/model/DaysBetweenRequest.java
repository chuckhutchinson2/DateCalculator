package org.example.datecalculator.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DaysBetweenRequest {
    LocalDate startDate;
    LocalDate endDate;
}
