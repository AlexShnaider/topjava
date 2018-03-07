package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements Storage<Meal> {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public MapMealStorage(List<Meal> inputMeals) {
        for (Meal meal : inputMeals) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        Meal answer = meals.put(id.intValue(), meal);
        id.incrementAndGet();
        return answer;
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
        if (this.id.intValue() > 0) {
            this.id.decrementAndGet();
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    public Integer getId() {
        return id.intValue();
    }
}
