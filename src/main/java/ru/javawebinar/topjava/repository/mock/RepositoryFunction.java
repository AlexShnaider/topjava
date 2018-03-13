package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface RepositoryFunction<T> {
    public T doThis(Map<Integer, Meal> userMeals);
}
