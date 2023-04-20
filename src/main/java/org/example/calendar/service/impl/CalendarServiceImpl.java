package org.example.calendar.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.calendar.model.Day;
import org.example.calendar.model.Month;
import org.example.calendar.model.Week;
import org.example.calendar.service.CalendarService;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CalendarServiceImpl implements CalendarService {
    @Override
    public List<Month> create(int year) {
        //  get the year in weeks including the previous years days or next years days if that year spans two
        List<Week> weeks = processYear(year);

        // break the weeks into months.  If a week spans two months it needs to appear on the calendar for both months.
        List<Month> months = processWeeks(weeks);

        // only return the months for the year we are in.
        return months.stream().filter(month -> month.getYear() == year).collect(Collectors.toList());
    }

    List<Week> processYear(int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);

        LocalDate startOfMonth = startOfYear.plusDays(0);
        List<Week> weeks = new ArrayList<>();
        Week week = new Week();
        week.setWeekDays(new ArrayList<>());
        log.info("startOfYear getDayOfWeek {} {} {} {}", startOfMonth, startOfMonth.getDayOfWeek(), startOfMonth.getDayOfMonth(), startOfMonth.get(WeekFields.of(Locale.US).weekOfYear()));

        // adjust the first week.  it might start in the previous year - e.g. Jan 1 might NOT be on a Sunday
        DayOfWeek weekStart = WeekFields.of(Locale.US).getFirstDayOfWeek();
        LocalDate previousDate = startOfMonth.with(TemporalAdjusters.previousOrSame(weekStart));

        startOfMonth = previousDate;

        log.debug("previousDate getDayOfWeek {} {} {} {}", previousDate, previousDate.getDayOfWeek(), previousDate.getDayOfMonth(), previousDate.get(WeekFields.of(Locale.US).weekOfYear()));
        log.debug("startOfMonth getDayOfWeek {} {} {} {}", startOfMonth, startOfMonth.getDayOfWeek(), startOfMonth.getDayOfMonth(), startOfMonth.get(WeekFields.of(Locale.US).weekOfYear()));
        log.debug("startOfYear getDayOfWeek {} {} {} {}", startOfYear, startOfYear.getDayOfWeek(), startOfYear.getDayOfMonth(), startOfYear.get(WeekFields.of(Locale.US).weekOfYear()));
        int dayNumber = 0;
        while (startOfMonth.getYear() <= startOfYear.getYear()) {
            LocalDate nextDay = startOfMonth.plusDays(0);
            while (nextDay.getMonth() == startOfMonth.getMonth()) {
                log.debug("next getDayOfWeek {} {} {}", nextDay.getDayOfWeek(), nextDay.getDayOfMonth(), dayNumber);
                Day day = new Day();
                day.setLocalDate(nextDay);
                week.getWeekDays().add(day);
                nextDay = nextDay.plusDays(1);

                dayNumber++;

                if (dayNumber == 7) {
                    weeks.add(week);
                    dayNumber = 0;
                    week = new Week();
                    week.setWeekDays(new ArrayList<>());
                }
            }
            startOfMonth = nextDay;
            log.debug("next month {}", startOfMonth);
        }

        // adjust the last week of the year - Dec 31 might NOT end on Saturday but we need the remainder of the year.
        if (dayNumber < 7) {
            LocalDate nextDay = startOfMonth.plusDays(0);
            while (dayNumber < 7) {
                log.debug("next getDayOfWeek {} {} {}", nextDay.getDayOfWeek(), nextDay.getDayOfMonth(), dayNumber);
                Day day = new Day();
                day.setLocalDate(nextDay);
                week.getWeekDays().add(day);
                nextDay = nextDay.plusDays(1);

                dayNumber++;

                if (dayNumber == 7) {
                    weeks.add(week);
                }
            }
        }
        return weeks;
    }

    List<Month> processWeeks(List<Week> weeks) {

        List<Month> months = new ArrayList<>();

        Month currentMonth = null;

        for (Week week : weeks) {
            Day firstDayOfWeek = week.getWeekDays().get(0);
            Day lastDayOfWeek = week.getWeekDays().get(week.getWeekDays().size() - 1);

            if (currentMonth == null) {
                currentMonth = new Month();
                months.add(currentMonth);
                currentMonth.setDay(firstDayOfWeek);
                currentMonth.setWeeks(new ArrayList<>());
            }

            currentMonth.getWeeks().add(week);

            if (firstDayOfWeek.getMonth() != lastDayOfWeek.getMonth()) {
                log.info("Week {} spans multiple months {} {}", firstDayOfWeek.getMonth(), lastDayOfWeek.getMonth() );
                currentMonth = new Month();
                currentMonth.setDay(lastDayOfWeek);
                currentMonth.setWeeks(new ArrayList<>());
                currentMonth.getWeeks().add(week);
                months.add(currentMonth);
            }

            if (lastDayOfWeek.isLastDayOfMonth()) {
                currentMonth = null;
            }
        }

        return months;
    }

}
