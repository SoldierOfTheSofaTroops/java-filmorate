package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.dto.FilmDTO;
import ru.yandex.practicum.filmorate.dal.dto.GenreDTO;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreDTO getGenreById(String id) {
        Genre genre = genreRepository.getGenreById(Long.parseLong(id));
        return GenreMapper.mapToGenreDTO(genre);
    }

    public Collection<GenreDTO> getGenres() {
        Collection<Genre> genreCollection = genreRepository.getAllGenres();
        return genreCollection.stream().map(GenreMapper::mapToGenreDTO).collect(Collectors.toList());
    }

    public FilmDTO getFilmsWithGenre(String filmId) {
        Film film = genreRepository.getFilmWithGenre(Long.parseLong(filmId));
        return GenreMapper.mapToFilmDTOCollection(film);
    }
}