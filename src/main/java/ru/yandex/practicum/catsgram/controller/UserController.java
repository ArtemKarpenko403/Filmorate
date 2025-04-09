package ru.yandex.practicum.catsgram.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.catsgram.model.User;

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
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        user.setId(nextId++);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Пользователь с ID " + user.getId() + " не найден"
            );
        }
        User existingUser = users.get(user.getId());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getLogin() != null) existingUser.setLogin(user.getLogin());
        if (user.getName() != null) existingUser.setName(user.getName());
        if (user.getBirthday() != null) existingUser.setBirthday(user.getBirthday());
        log.info("Обновлен пользователь: {}", existingUser);
        return existingUser;
    }
}