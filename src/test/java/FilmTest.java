import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.catsgram.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Validator validator;
    private Film validFilm;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        validFilm = new Film();
        validFilm.setName("Valid Film");
        validFilm.setDescription("Normal description");
        validFilm.setReleaseDate(LocalDate.of(2000, 1, 1));
        validFilm.setDuration(120);
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        Set<ConstraintViolation<Film>> violations = validator.validate(validFilm);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenNameIsBlank_thenValidationFails() {
        validFilm.setName("  ");
        Set<ConstraintViolation<Film>> violations = validator.validate(validFilm);
        assertEquals(1, violations.size());
        assertEquals("Название фильма не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionTooLong_thenValidationFails() {
        validFilm.setDescription("a".repeat(201));
        Set<ConstraintViolation<Film>> violations = validator.validate(validFilm);
        assertEquals(1, violations.size());
        assertEquals("Описание не должно превышать 200 символов", violations.iterator().next().getMessage());
    }

    @Test
    void whenReleaseDateBefore1895_thenValidationFails() {
        validFilm.setReleaseDate(LocalDate.of(1895, 12, 27));
        Set<ConstraintViolation<Film>> violations = validator.validate(validFilm);
        assertEquals(1, violations.size());
        assertEquals("Дата должна быть не раньше 1895-12-28", violations.iterator().next().getMessage());
    }

    @Test
    void whenDurationNegative_thenValidationFails() {
        validFilm.setDuration(-10);
        Set<ConstraintViolation<Film>> violations = validator.validate(validFilm);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность должна быть положительной", violations.iterator().next().getMessage());
    }
}