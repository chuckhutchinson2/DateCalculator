package org.example.calendar.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

@Data
public class Day {
    LocalDate localDate;

    public boolean isLastDayOfMonth() {
        LocalDate localDate1 = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return localDate.equals(localDate1);
    }

    public int getYear() { return localDate.getYear(); }
    public Integer getDayOfMonth() {
        return localDate.getDayOfMonth();
    }
    public Month getMonth() {
        return localDate.getMonth();
    }
    public String getDayOfWeek() {
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
    }
}
