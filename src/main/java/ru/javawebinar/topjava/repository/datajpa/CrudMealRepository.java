package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Transactional
    int deleteByUserIdAndId(int userId, int id);

    @Override
    @Transactional
    Meal save(Meal meal);

    Optional<Meal> findByUserIdAndId(int userId, int id);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user u WHERE u.id=?1 AND m.id=?2")
    Optional<Meal> getWithUser(int userId, int id);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(
            int userId, LocalDateTime startDate, LocalDateTime endDate);
}
