package org.example.datecalculator.pdf.impl.model;

import lombok.Data;

import java.util.List;

@Data
public class Month {
    private java.time.Month month;
    private List<Week> weeks;
}
