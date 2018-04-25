package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public final class LocalTimeFormatter implements Formatter<LocalTime> {

    public String print(LocalTime time, Locale locale) {
        if (time == null) {
            return "";
        }
        return time.toString();
    }

    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        if (formatted == null || formatted.length() == 0) {
            return null;
        }
        return LocalTime.parse(formatted);
    }
}