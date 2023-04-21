package org.example.calendar.service;

import org.example.calendar.model.Month;

import java.io.File;
import java.util.List;

public interface CalendarService {

    File createCalendar(int year, String filename);

    List<Month> create(int year);
}
