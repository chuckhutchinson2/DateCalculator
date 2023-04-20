package org.example.calendar.model;

import lombok.Data;

import java.util.List;

@Data
public class Week {
    List<Day> weekDays;
}
