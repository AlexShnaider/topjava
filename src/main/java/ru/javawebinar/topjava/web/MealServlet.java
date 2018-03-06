package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Config;
import ru.javawebinar.topjava.Storage.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Storage<Meal> storage = Config.getInstance().getStorage();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        String description = request.getParameter("description");
        String dateTimeParts = request.getParameter("dateTime").trim();
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(dateTimeParts, dateTimeFormatter);
        } catch (DateTimeParseException e) {
        }
        Integer calories = null;
        try {
            calories = Integer.valueOf(request.getParameter("calories"));
        } catch (NumberFormatException e) {
        }
        Meal newMeal = null;
        if (description != null && description.length() != 0 && dateTime != null && calories != null) {
            newMeal = new Meal(id, dateTime, description, calories);
        }
        Meal meal = storage.get(id);
        if (newMeal != null) {
            if (meal != null) {
                log.debug("Update Meal id = " + meal.getId());
                storage.update(newMeal);
            } else {
                log.debug("Save new Meal id = " + newMeal.getId());
                storage.save(newMeal);
            }
        }
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                storage.getAllSorted(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        request.getRequestDispatcher("/viewMeals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("meals",
                MealsUtil.getFilteredWithExceeded(storage.getAllSorted(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        String action = request.getParameter("action");
        switch (action) {
            case "delete":
                log.debug("Delete Meal, id = " + request.getParameter("id"));
                storage.delete(Integer.valueOf(request.getParameter("id")));
            case "view":
                request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                        storage.getAllSorted(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                log.debug("Redirect to viewMeals.jsp");
                request.getRequestDispatcher("/viewMeals.jsp").forward(request, response);
                break;
            case "add":
                log.debug("Create new empty Meal");
                request.setAttribute("meal", new Meal(storage.getAllSorted().size(), null, null, 0));
                log.debug("Redirect to editMeal.jsp");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case "edit":
                request.setAttribute("meal", storage.get(Integer.valueOf(request.getParameter("id"))));
                log.debug("Redirect to editMeal.jsp");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
        }
    }
}
