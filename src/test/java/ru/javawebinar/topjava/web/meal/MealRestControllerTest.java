package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.web.json.JsonUtil.writeValue;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(MEAL1)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk());
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(AuthorizedUser.id()), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Brunch");
        updated.setUser(USER);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk())
                .andDo(print());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(LocalDateTime.of(2018,12,10,13,0),"Brunch",100);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated())
                .andDo(print());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        expected.setId(returned.getId());
        assertMatch(mealService.getAll(AuthorizedUser.id()), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(getWithExceeded(MEALS, DEFAULT_CALORIES_PER_DAY)))));
    }

    /*@Test
    public void testGetBetweenDateTimeFiltered() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filtered")
                .param("startDateTime", "2015-05-30T10:00:00")
                .param("endDateTime", "2015-05-30T18:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(getWithExceeded(Arrays.asList(MEAL2, MEAL1),DEFAULT_CALORIES_PER_DAY)))));
    }*/

    @Test
    public void testGetBetweenDateTimeFiltered() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filtered")
                .param("startDate", "2015-05-30")
                .param("startTime", "10:00")
                .param("endDate", "2015-05-30")
                .param("endTime", "18:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(
                        Arrays.asList(createWithExceed(MEAL2, false), createWithExceed(MEAL1, false))))));
    }

    @Test
    public void testGetBetweenDateFiltered() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filtered")
                .param("startDate", "2015-05-30")
                .param("endDate", "2015-05-30"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(Arrays.asList(
                        createWithExceed(MEAL3, false),
                        createWithExceed(MEAL2, false),
                        createWithExceed(MEAL1, false))))));
    }

    @Test
    public void testGetBetweenTimeFiltered() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filtered")
                .param("startTime", "10:00")
                .param("endTime", "18:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(Arrays.asList(
                        createWithExceed(MEAL5, true),
                        createWithExceed(MEAL4, true),
                        createWithExceed(MEAL2, false),
                        createWithExceed(MEAL1, false))))));
    }
}
