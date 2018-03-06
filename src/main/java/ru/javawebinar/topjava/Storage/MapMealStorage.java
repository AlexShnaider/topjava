package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMealStorage implements Storage<Meal> {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private Integer id = 0;

    public MapMealStorage(List<Meal> inputMeals) {
        for (Meal meal : inputMeals) {
            save(meal);
        }
    }

    @Override
    public void clear() {
        meals.clear();
        id = 0;
    }

    @Override
    public void save(Meal meal) {
        meals.put(id++, meal);
    }

    @Override
    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
        if (this.id > 0) {
            this.id--;
        }
    }

    @Override
    public List<Meal> getAllSorted() {
        List<Meal> answer = new ArrayList<>(meals.values());
        Collections.sort(answer);
        return answer;
    }

    public Integer getId() {
        return id;
    }
}
