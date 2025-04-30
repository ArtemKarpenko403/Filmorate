package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.MpaRepositoryImpl;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaRepositoryImpl mpaRepository;

    public MpaController(MpaRepositoryImpl mpaRepository) {
        this.mpaRepository = mpaRepository;
    }

    @GetMapping
    public Collection<Mpa> findAll() {
        return mpaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mpa findById(@PathVariable int id) {
        return mpaRepository.findById(id);
    }
}