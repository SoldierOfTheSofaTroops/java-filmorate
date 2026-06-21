package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.request.film.post.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.PostFilmResponse;
import ru.yandex.practicum.filmorate.exception.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.AbstractFilmRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final AbstractFilmRepository filmRepository;

    public PostFilmResponse postFilm(PostFilmRequest postFilmRequest) {
        Film film = FilmMapper.mapFromPostFilmRequestToFilm(postFilmRequest);
        return filmRepository.postFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmRepository.getFilms();
    }

    public Film updateFilm(Film film) {
        return filmRepository.updateFilm(film);
    }

    public int addLike(long filmId, long userId) {
        return filmRepository.addLike(filmId, userId);
    }

    public Film removeLike(long filmId, long userId) {
        return null;
    }

    public Collection<Film> getPopular(Integer count) {
//        if (count > 0) {
//            Collection<Film> films = filmStorage.getFilms();
//            return films.stream()
//                    .sorted(Comparator.comparingLong(Film::getLikes).reversed())
//                    .limit(count)
//                    .collect(Collectors.toList());
//        }
        throw new ConditionsNotMetException("Incorrect count value");
    }
}