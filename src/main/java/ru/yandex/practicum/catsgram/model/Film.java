package ru.yandex.practicum.catsgram.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.catsgram.validation.MinDate;
import java.time.LocalDate;

@Data
public class Film {
    private Integer id;
    
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    
    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;
    
    @NotNull(message = "Дата релиза обязательна")
    @MinDate(value = "1895-12-28", message = "Фильмы не могли выходить раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    
    @Positive(message = "Продолжительность должна быть положительной")
    private Integer duration;
}