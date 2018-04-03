package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Transactional
    int deleteByUserIdAndId(int userId, int id);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    Optional<Meal> findByUserIdAndId(int userId, int id);

    @Transactional
    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    @Transactional
    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(
            int userId, LocalDateTime startDate, LocalDateTime endDate);
}
