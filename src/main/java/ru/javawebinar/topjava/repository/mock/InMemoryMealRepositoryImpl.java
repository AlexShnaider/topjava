package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return repositoryDo(userId, userMeals -> {
            if (meal.isNew()) {
                log.info("Save {}", meal);
                meal.setId(counter.incrementAndGet());
                userMeals.put(meal.getId(), meal);
                return meal;
            }
            log.info("Update {}", meal);
            return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        });
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return repositoryDo(userId, userMeals -> {
            log.info("Delete {}", mealId);
            return userMeals.remove(mealId) != null;
        });
    }

    @Override
    public Meal get(int mealId, int userId) {
        return repositoryDo(userId, userMeals -> {
            log.info("Get {}", mealId);
            return userMeals.get(mealId);
        });
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("GetAll {}");
        return repositoryDo(userId, userMeals -> userMeals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }

    private <T> T repositoryDo(int userId, RepositoryFunction<T> func) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) {
            userMeals = new HashMap<>();
        }
        T answer = func.doThis(userMeals);
        repository.put(userId, userMeals);
        return answer;
    }
}

