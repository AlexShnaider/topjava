package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal implements Comparable<Meal> {

    private final int id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public int compareTo(Meal o) {
        int result = dateTime.compareTo(o.dateTime);
        if (result != 0) {
            return result;
        }
        result = getMealNumber(description) - getMealNumber(o.description);
        if (result != 0) {
            return result;
        }
        return calories - o.calories;
    }

    private int getMealNumber(String mealName) {
        int mealNum;
        switch (mealName) {
            case "Breakfast":
                mealNum = 1;
                break;
            case "Lunch":
                mealNum = 2;
                break;
            case "Dinner":
                mealNum = 3;
                break;
            default:
                mealNum = 0;
        }
        return mealNum;
    }
}
