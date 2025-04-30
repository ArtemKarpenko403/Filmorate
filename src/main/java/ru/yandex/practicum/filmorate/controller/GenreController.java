package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepositoryImpl;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreRepositoryImpl genreRepositoryImpl;

    public GenreController(GenreRepositoryImpl genreRepositoryImpl) {
        this.genreRepositoryImpl = genreRepositoryImpl;
    }

    @GetMapping
    public Collection<Genre> findAll() {
        return genreRepositoryImpl.findAll();
    }

    @GetMapping("/{id}")
    public Genre findById(@PathVariable int id) {
        return genreRepositoryImpl.findById(id);
    }
}