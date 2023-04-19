package org.example.datecalculator.pdf.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.datecalculator.config.ThymeLeafConfig;
import org.example.datecalculator.pdf.impl.model.Day;
import org.example.datecalculator.pdf.impl.model.Month;
import org.example.datecalculator.pdf.impl.model.Product;
import org.example.datecalculator.pdf.impl.model.Week;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

import java.time.LocalDate;


@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class PdfGeneratorImplTest {

    private PdfGeneratorImpl pdfGenerator;

    @Before
    public void before() {
        ThymeLeafConfig thymeLeafConfig = new ThymeLeafConfig();
        ITemplateResolver iTemplateResolver = thymeLeafConfig.thymeleafTemplateResolver();
        pdfGenerator = new PdfGeneratorImpl(thymeLeafConfig.thymeleafTemplateEngine(iTemplateResolver));
    }

    @Test
    public void testGenerateCalendar1() {

        Map<DayOfWeek, Integer> week = new HashMap<>();
        for(DayOfWeek dayOfWeek : DayOfWeek.values()) {
            log.info("DayOfWeek {}", dayOfWeek);
        }
    }


    @Test
    public void testGenerateCalendar() {

        List<Month> months = getYear(2023);

        log.info("months {}", months);


        Map<String, Object> model = new HashMap<>();
        model.put("months", months)
        File file = pdfGenerator.generatePDF("calendar.pdf", "calendar.html", model);

        log.info("File {}", file.getAbsolutePath());
    }

    List<Month> getYear(int year) {
        //  get the year in weeks including the previous years days or next years days if that year spans two
        List<Week> weeks = processYear(year);

        // break the weeks into months.  If a week spans two months it needs to appear on the calendar for both months.

        return processWeeks(weeks);
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
                currentMonth.setMonth(firstDayOfWeek.getMonth());
                currentMonth.setWeeks(new ArrayList<>());
            }

            currentMonth.getWeeks().add(week);

            if (firstDayOfWeek.getMonth() != lastDayOfWeek.getMonth()) {
                log.info("Week {} spans multiple months {} {}", firstDayOfWeek.getMonth(), lastDayOfWeek.getMonth() );
                currentMonth = new Month();
                currentMonth.setMonth(lastDayOfWeek.getMonth());
                currentMonth.setWeeks(new ArrayList<>());
                currentMonth.getWeeks().add(week);
                months.add(currentMonth);
            }

            if (lastDayOfWeek.isLastDayOfMonth()) {
                currentMonth = null;
            }
        }

        Month startMonth = months.get(0);
        int startIndex = 0;
        if (java.time.Month.DECEMBER == startMonth.getMonth()) {
            startIndex = 1;
        }
        int endIndex = months.size() - 1;
        Month endMonth = months.get(endIndex);

        if (java.time.Month.JANUARY == endMonth.getMonth()) {
            endIndex--;
        }

        return months.subList(startIndex, endIndex+1);
    }

    @Test
    public void testGeneratePdf() {
        List<Product> products = new ArrayList<>();
        products.add(createProduct("INTC", "8088", "8088 processor"));
        products.add(createProduct("INTC", "80286", "80286 processor"));
        products.add(createProduct("INTC", "80386", "80386 processor"));
        products.add(createProduct("INTC", "80486", "80486 processor"));
        Map<String, Object> model = new HashMap<>();
        model.put("products" ,products);
        File file = pdfGenerator.generatePDF("products.pdf", "products.html", model);

        log.info("File {}", file.getAbsolutePath());
    }

    private Product createProduct(String vendor, String name, String description) {
        Product product = new Product();
        product.setVendor(vendor);
        product.setName(name);
        product.setDescription(description);
        return product;
    }
}
