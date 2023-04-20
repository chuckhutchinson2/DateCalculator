package org.example.calendar.model;

import lombok.Data;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Data
public class Month {
    private Day day;
    private List<Week> weeks;
    public int getYear() {
        return day.getYear();
    }
    public String getMonthName() {
        return day.getMonthName();
    }
}
