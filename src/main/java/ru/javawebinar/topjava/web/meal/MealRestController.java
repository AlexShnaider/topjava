package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final int userId = AuthorizedUser.id();

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Meal> getAllFiltered(LocalDate startDate, LocalDate endDate) {
        log.info("getAll");
        return service.getAllFiltered(startDate, endDate);
    }

    public List<Meal> getAllFiltered(LocalTime startTime, LocalTime endTime) {
        log.info("getAll");
        return service.getAllFiltered(startTime, endTime);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal) throws IllegalArgumentException {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        ValidationUtil.assureUserIdConsistent(meal, userId);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal) throws IllegalArgumentException {
        log.info("update {}", meal);
        ValidationUtil.checkNotNew(meal);
        ValidationUtil.assureUserIdConsistent(meal, userId);
        service.update(meal);
    }

}