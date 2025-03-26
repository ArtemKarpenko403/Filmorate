import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.catsgram.CatsgramApplication;
import ru.yandex.practicum.catsgram.model.Film;
import ru.yandex.practicum.catsgram.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = CatsgramApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ControllerValidationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenCreateInvalidFilm_thenBadRequest() {
        Film film = new Film();
        film.setName("");
        film.setReleaseDate(null);

        ResponseEntity<String> response = restTemplate.postForEntity("/films", film, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Название фильма не может быть пустым"));
        assertTrue(response.getBody().contains("Дата релиза обязательна"));
    }

    @Test
    void whenCreateInvalidUser_thenBadRequest() {
        User user = new User();
        user.setEmail("invalid");
        user.setLogin("");

        ResponseEntity<String> response = restTemplate.postForEntity("/users", user, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Некорректный формат email"));
        assertTrue(response.getBody().contains("Логин не может быть пустым"));
    }
}