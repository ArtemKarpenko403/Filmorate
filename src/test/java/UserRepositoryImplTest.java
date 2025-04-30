
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepositoryImpl;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserRepositoryImpl.class)
class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void findUserById_shouldReturnUserWhenExists() {
        // Создаем тестового пользователя
        User newUser = User.builder()
                .email("test@example.com")
                .login("testlogin")
                .name("Test User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        // Сохраняем пользователя в БД
        User savedUser = userRepository.create(newUser);

        // Ищем пользователя по ID
        User foundUser = userRepository.getById(savedUser.getId());

        // Проверяем результаты
        assertThat(foundUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", savedUser.getId())
                .hasFieldOrPropertyWithValue("email", "test@example.com")
                .hasFieldOrPropertyWithValue("login", "testlogin");
    }

    @Test
    void createUser_shouldSaveUserWithGeneratedId() {
        User newUser = User.builder()
                .email("new@example.com")
                .login("newuser")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        User createdUser = userRepository.create(newUser);

        assertThat(createdUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("email", "new@example.com")
                .hasFieldOrProperty("id");
    }

}