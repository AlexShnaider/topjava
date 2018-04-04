package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        //the call to method get has to be passed through bean crudMealRepository https://stackoverflow.com/a/27269886
        if (!meal.isNew() && !crudMealRepository.findByUserIdAndId(userId, meal.getId()).isPresent()) {
            return null;
        }
        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByUserIdAndId(userId, id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.findByUserIdAndId(userId, id).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }
}
