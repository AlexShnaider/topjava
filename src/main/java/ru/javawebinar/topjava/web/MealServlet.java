package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Storage<Meal> storage = new MapMealStorage(Arrays.asList(
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500),
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Lunch", 1000),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Dinner", 500),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 1000),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Lunch", 500),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Dinner", 510)
    ));

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        String description = request.getParameter("description");
        String dateTimeParts = request.getParameter("dateTime").trim();
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeParts, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            dateTime = null;
        }
        Integer calories;
        try {
            calories = Integer.valueOf(request.getParameter("calories"));
        } catch (NumberFormatException e) {
            calories = null;
        }
        if (description != null && description.length() != 0 && dateTime != null && calories != null) {
            if (id == -1) {
                log.debug("Save new Meal id = " + storage.getId());
                storage.save(new Meal(storage.getId(), dateTime, description, calories));
            } else {
                log.debug("Update Meal id = " + id);
                storage.update(new Meal(id, dateTime, description, calories));
            }
        }
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        request.getRequestDispatcher("/viewMeals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("meals",
                MealsUtil.getFilteredWithExceeded(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                    storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            log.debug("Redirect to viewMeals.jsp");
            request.getRequestDispatcher("/viewMeals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                log.debug("Delete Meal, id = " + request.getParameter("id"));
                storage.delete(Integer.valueOf(request.getParameter("id")));
                response.sendRedirect("meals");
                break;
            case "view":
                request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                        storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                log.debug("Redirect to viewMeals.jsp");
                request.getRequestDispatcher("/viewMeals.jsp").forward(request, response);
                break;
            case "add":
                log.debug("Create new empty Meal");
                request.setAttribute("meal", new Meal(-1, null, null, 0));
                log.debug("Redirect to editMeal.jsp");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case "edit":
                request.setAttribute("meal", storage.get(Integer.valueOf(request.getParameter("id"))));
                log.debug("Redirect to editMeal.jsp");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
        }
    }
}
