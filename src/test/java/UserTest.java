import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private Validator validator;
    private User validUser;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        validUser = new User();
        validUser.setEmail("valid@email.com");
        validUser.setLogin("validLogin");
        validUser.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEmailInvalid_thenValidationFails() {
        validUser.setEmail("invalid-email");
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);
        assertEquals(1, violations.size());
        assertEquals("Некорректный формат email", violations.iterator().next().getMessage());
    }

    @Test
    void whenLoginContainsSpaces_thenValidationFails() {
        validUser.setLogin("login with spaces");
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);
        assertEquals(1, violations.size());
        assertEquals("Логин не должен содержать пробелы", violations.iterator().next().getMessage());
    }

    @Test
    void whenBirthdayInFuture_thenValidationFails() {
        validUser.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);
        assertEquals(1, violations.size());
        assertEquals("Дата рождения не может быть в будущем", violations.iterator().next().getMessage());
    }

}