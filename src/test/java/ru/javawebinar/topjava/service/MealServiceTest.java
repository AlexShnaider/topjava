package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Новая еда", 1000);
        Meal answer = mealService.create(newMeal, USER_ID);
        newMeal.setId(answer.getId());
        assertMatch(mealService.getAll(USER_ID), newMeal, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateDate() {
        Meal newMeal = new Meal(MEAL_1);
        newMeal.setId(null);
        mealService.create(newMeal, USER_ID);
    }

    @Test
    public void update() {
        Meal newMeal = new Meal(MEAL_1);
        newMeal.setDescription("Другая еда");
        newMeal.setCalories(10);
        newMeal.setDateTime(LocalDateTime.now());
        Meal answer = mealService.update(newMeal, USER_ID);
        assertMatch(answer, newMeal);
        assertMatch(mealService.getAll(USER_ID), newMeal, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        mealService.update(MEAL_1, ADMIN_ID);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_1.getId(), USER_ID);
        assertMatch(mealService.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void get() {
        Meal answer = mealService.get(MEAL_1.getId(), USER_ID);
        assertMatch(answer, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void getAll() {
        assertMatch(mealService.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
        assertMatch(mealService.getAll(1), Collections.emptyList());
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);
        assertMatch(mealService.getBetweenDateTimes(startDateTime, endDateTime, USER_ID), MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2015, Month.MAY, 31);
        LocalDate endtDate = LocalDate.of(2015, Month.MAY, 31);
        assertMatch(mealService.getBetweenDates(startDate, endtDate, USER_ID), MEAL_6, MEAL_5, MEAL_4);
    }
}