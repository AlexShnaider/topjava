package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MealTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @NotBlank
    @Size(max = 100)
    private String description;

    @Range(min = 0, max = UserUtil.DEFAULT_CALORIES_PER_DAY)
    @NotNull
    private Integer calories;

    public MealTo() {
    }

    public MealTo(Integer id, String dateTime, String description, int calories) {
        super(id);
        this.dateTime = LocalDateTime.parse(dateTime);
        this.description = description;
        this.calories = calories;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
