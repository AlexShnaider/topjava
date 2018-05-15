package ru.javawebinar.topjava.web.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Component
public class MealFormValidator implements Validator {

    @Autowired
    private MealService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Meal.class.equals(aClass);
    }

    @Override
    public void validate(Object oMeal, Errors errors) {
        Meal meal = (Meal) oMeal;
        List<Meal> existedMeals =
                service.getBetweenDateTimes(meal.getDateTime(), meal.getDateTime(), AuthorizedUser.id());
        !!!!!if (!existedMeals.isEmpty() && existedMeals.get(0).getId()!= meal.getId()) {
            errors.rejectValue("dateTime", "", "Meal with such dateTime already exists");
        }
    }
}
