package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {
    Film create(Film film);

    Film update(Film film);

    Film getFilmById(int id);

    Collection<Film> findAll();

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);
}