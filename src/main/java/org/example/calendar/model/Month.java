package org.example.calendar.model;

import lombok.Data;

import java.util.List;

@Data
public class Month {
    private String image;
    private Day day;
    private List<Week> weeks;
    public int getYear() {
        return day.getYear();
    }
    public String getMonthName() {
        return day.getMonthName();
    }
}
