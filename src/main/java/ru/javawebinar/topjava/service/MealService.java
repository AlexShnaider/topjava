package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal create(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    void update(Meal meal) throws NotFoundException, IllegalArgumentException;

    List<Meal> getAll();

    List<Meal> getAllFiltered(LocalTime startTime, LocalTime endTime);

    List<Meal> getAllFiltered(LocalDate startDate, LocalDate endDate);
}