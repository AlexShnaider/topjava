package ru.javawebinar.topjava.util;

import java.time.LocalDate;

public class DateUtil {
    public static boolean isSameDates(LocalDate one, LocalDate another) {
        return one.compareTo(another) == 0;
    }
}
