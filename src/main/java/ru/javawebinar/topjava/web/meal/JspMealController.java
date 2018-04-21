package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@Controller
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()),
                MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }

    @GetMapping("/meals/delete")
    public String meals(HttpServletRequest request) {
        service.delete(Integer.valueOf(request.getParameter("id")), AuthorizedUser.id());
        return "redirect:/meals";
    }

    @PostMapping("/meals/filter")
    public String meals(Model model, HttpServletRequest request) {
        LocalDateTime startDateTime = LocalDateTime.of(
                minDateIfEmpty(request.getParameter("startDate")),
                minTimeIfEmpty(request.getParameter("startTime")));
        LocalDateTime endDateTime = LocalDateTime.of(
                maxDateIfEmpty(request.getParameter("endDate")),
                maxTimeIfEmpty(request.getParameter("endTime")));
        model.addAttribute("meals", MealsUtil.getWithExceeded(
                service.getBetweenDateTimes(startDateTime, endDateTime, AuthorizedUser.id()),
                MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }
/*
    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }*/


}
