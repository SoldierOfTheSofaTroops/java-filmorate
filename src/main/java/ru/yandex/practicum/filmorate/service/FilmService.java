package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.FilmDTO;
import ru.yandex.practicum.filmorate.dal.dto.GenreDTO;
import ru.yandex.practicum.filmorate.dal.dto.MpaDTO;
import ru.yandex.practicum.filmorate.dal.dto.request.film.post.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.PostFilmResponse;
import ru.yandex.practicum.filmorate.exception.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.mapper.MPAMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.storage.film.AbstractFilmRepository;

import java.util.Collection;
import java.util.stream.Collectors;

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

    // ===========> MPA Service <==============

    public MpaDTO getMPAById(String id) {
        MPARating mpaRating = filmRepository.getMpaById(Long.parseLong(id));
        return MPAMapper.mapToMpaDTO(mpaRating);
    }

    public Collection<MpaDTO> getAllMPA() {
        Collection<MPARating> mpaRatings = filmRepository.getAllMPA();
        return MPAMapper.mapToMpaDTOCollection(mpaRatings);
    }

    // ===========> Genre Service <==============

    public GenreDTO getGenreById(String id) {
        Genre genre = filmRepository.getGenreById(Long.parseLong(id));
        return GenreMapper.mapToGenreDTO(genre);
    }

    public Collection<GenreDTO> getGenres() {
        Collection<Genre> genreCollection = filmRepository.getAllGenres();
        return genreCollection.stream().map(GenreMapper::mapToGenreDTO).collect(Collectors.toList());
    }

    public FilmDTO getFilmsWithGenre(String filmId) {
        Film film = filmRepository.getFilmWithGenre(Long.parseLong(filmId));
        return GenreMapper.mapToFilmDTOCollection(film);
    }
}