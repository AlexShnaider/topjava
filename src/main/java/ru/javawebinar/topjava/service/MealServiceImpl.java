package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;
    private final int userId = AuthorizedUser.id();

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void update(Meal meal) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getAllFiltered(LocalTime startTime, LocalTime endTime) {
        return repository.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFiltered(LocalDate startDate, LocalDate endDate) {
        return repository.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}