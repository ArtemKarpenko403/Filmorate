package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    public Film create(Film film) {
        validateFilm(film);
        return filmRepository.create(film);
    }

    public Film update(Film film) {
        validateFilm(film);
        getById(film.getId()); // Проверка существования фильма
        return filmRepository.update(film);
    }

    public Film getById(int id) {
        Film film = filmRepository.getFilmById(id);
        if (film == null) {
            throw new NotFoundException("Film with id " + id + " not found");
        }
        return film;
    }

    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    public void addLike(int filmId, int userId) {
        Film film = getById(filmId);
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new NotFoundException("User with id " + userId + " not found");
        }
        filmRepository.addLike(film.getId(), user.getId());
    }

    public void removeLike(int filmId, int userId) {
        Film film = getById(filmId);
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new NotFoundException("User with id " + userId + " not found");
        }
        filmRepository.removeLike(film.getId(), user.getId());
    }

    public Collection<Film> getPopular(int count) {
        if (count <= 0) {
            throw new ValidationException("Count must be positive");
        }
        return filmRepository.getPopularFilms(count);
    }

    private void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Release date must be after 1895-12-28");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Duration must be positive");
        }
    }
}