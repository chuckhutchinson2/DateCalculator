package org.example.calendar.service;

import org.example.calendar.model.Month;

import java.util.List;

public interface CalendarService {

    List<Month> create(int year);
}
