package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.AbstractUserRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final AbstractUserRepository abstractUserRepository;

    public Film postFilm(Film film) {
        return filmStorage.postFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film addLike(long id, long userId) {
        return null;
    }

    public Film removeLike(long id, long userId) {
        return null;
    }

    public Collection<Film> getPopular(Integer count) {
        if (count > 0) {
            Collection<Film> films = filmStorage.getFilms();
            return films.stream()
                    .sorted(Comparator.comparingLong(Film::getLikes).reversed())
                    .limit(count)
                    .collect(Collectors.toList());
        }
        throw new ConditionsNotMetException("Incorrect count value");
    }
}