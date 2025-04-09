package ru.yandex.practicum.catsgram.storage.film;


import ru.yandex.practicum.catsgram.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Film getFilmById(int id);

    Collection<Film> findAll();

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);
}