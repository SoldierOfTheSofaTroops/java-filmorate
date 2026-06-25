package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.dal.dto.response.film.post.PostFilmResponse;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.Collection;

public interface AbstractFilmRepository {

    PostFilmResponse postFilm(Film film);

    Collection<Film> getFilms();

    Film getFilmById(long id);

    Film updateFilm(Film film);

    int addLike(long filmId, long userId);

    int removeLike(long filmId, long userId);

    MPARating getMpaById(long mpaId);

    Collection<MPARating> getAllMPA();

    Genre getGenreById(long id);

    Collection<Genre> getAllGenres();

    Film getFilmWithGenre(long filmId);

    boolean isFilmExists(long id);
}
