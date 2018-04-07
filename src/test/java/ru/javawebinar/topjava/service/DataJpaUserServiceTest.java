package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepositoryImpl;

import static ru.javawebinar.topjava.MealTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    DataJpaUserRepositoryImpl dataJpaUserRepository;

    @Test
    public void getWithMeals() throws Exception {
        User user = dataJpaUserRepository.getWithMeals(USER_ID);
        UserTestData.assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getWithZeroMeals() throws Exception {
        User user = dataJpaUserRepository.getWithMeals(USER_WITHOUT_MEALS.getId());
        UserTestData.assertMatch(user, USER_WITHOUT_MEALS);
        assertThat(user.getMeals()).isEmpty();
    }
}
