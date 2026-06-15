package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.dal.dto.request.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.PostFilmResponse;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface AbstractFilmRepository {

    PostFilmResponse postFilm(Film film);

    Collection<Film> getFilms();

    Film getFilmById(long id);

    Film updateFilm(Film film);

    boolean isFilmExists(long id);
}
