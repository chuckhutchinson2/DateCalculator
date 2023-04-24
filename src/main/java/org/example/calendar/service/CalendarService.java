package org.example.calendar.service;

import org.example.calendar.model.Month;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

public interface CalendarService {

    void createCalendar(int year, OutputStream outputStream);

    File createCalendar(int year, String filename);

    void setTemplate(String template);

    void setTheme(String theme);

    List<Month> create(int year);
}
