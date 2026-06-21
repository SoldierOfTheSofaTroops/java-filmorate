package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.FilmDTO;
import ru.yandex.practicum.filmorate.dal.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreMapper {

    public static GenreDTO mapToGenreDTO(Genre genre){
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    public static FilmDTO mapToFilmDTOCollection(Film film){
            FilmDTO filmDTO = new FilmDTO();
            filmDTO.setId(film.getId());
            filmDTO.setName(film.getName());
            filmDTO.setDescription(film.getDescription());
            filmDTO.setDuration(film.getDuration());
            filmDTO.setMpa(film.getMpa());
            filmDTO.setReleaseDate(film.getReleaseDate());
            filmDTO.setGenres(film.getGenres());
        return filmDTO;
    }
}
