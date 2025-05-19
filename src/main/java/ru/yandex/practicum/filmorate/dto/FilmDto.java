package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class FilmDto {
    @NotBlank
    private String title;

    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

    @NotNull
    private Integer mpaId;

    private Set<Integer> genreIds;
}