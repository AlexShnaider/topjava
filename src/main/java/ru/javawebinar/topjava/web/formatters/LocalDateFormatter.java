package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate> {

    public String print(LocalDate date, Locale locale) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    public LocalDate parse(String formatted, Locale locale) throws ParseException {
        if (formatted == null || formatted.length() == 0) {
            return null;
        }
        return LocalDate.parse(formatted);
    }
}