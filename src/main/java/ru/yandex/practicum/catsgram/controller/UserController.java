package ru.yandex.practicum.catsgram.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.exception.ValidationException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        checkEmailUniqueness(user.getEmail());
        user.setId(nextId++);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с указанным id не найден");
        }
        if (user.getEmail() != null && !user.getEmail().equals(users.get(user.getId()).getEmail())) {
            checkEmailUniqueness(user.getEmail());
        }
        User existingUser = users.get(user.getId());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getLogin() != null) existingUser.setLogin(user.getLogin());
        if (user.getName() != null) existingUser.setName(user.getName());
        if (user.getBirthday() != null) existingUser.setBirthday(user.getBirthday());
        log.info("Обновлен пользователь: {}", existingUser);
        return existingUser;
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Некорректный email: {}", user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("Некорректный логин: {}", user.getLogin());
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Некорректная дата рождения: {}", user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

    private void checkEmailUniqueness(String email) {
        if (users.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            log.warn("Попытка добавить существующий email: {}", email);
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
    }
}