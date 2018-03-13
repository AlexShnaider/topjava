package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private static final int USER_ID = AuthorizedUser.id();
    private static final int USER_CALORIES = AuthorizedUser.getCaloriesPerDay();

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(USER_ID), USER_CALORIES);
    }

    public List<MealWithExceed> getAllFiltered(
            LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getAll");
        return MealsUtil.getFilteredWithExceeded(
                service.getAll(USER_ID), USER_CALORIES,
                startDate == null ? LocalDate.MIN : startDate,
                startTime == null ? LocalTime.MIN : startTime,
                endDate == null ? LocalDate.MAX : endDate,
                endTime == null ? LocalTime.MAX : endTime);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, USER_ID);
    }

    public Meal create(Meal meal) throws IllegalArgumentException {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, USER_ID);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, USER_ID);
    }

    public void update(Meal meal) throws IllegalArgumentException {
        log.info("update {}", meal);
        ValidationUtil.checkNotNew(meal);
        service.update(meal, USER_ID);
    }

}