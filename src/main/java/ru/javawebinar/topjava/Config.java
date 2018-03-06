package ru.javawebinar.topjava;

import ru.javawebinar.topjava.Storage.MapMealStorage;
import ru.javawebinar.topjava.Storage.Storage;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class Config {
    private static Config ourInstance = new Config();
    private final Storage<Meal> storage;

    private Config() {
        storage = new MapMealStorage(Arrays.asList(
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500),
                new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Lunch", 1000),
                new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Dinner", 500),
                new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 1000),
                new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Lunch", 500),
                new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Dinner", 510)
        ));
    }

    public static Config getInstance() {
        return ourInstance;
    }

    public Storage<Meal> getStorage() {
        return storage;
    }
}
