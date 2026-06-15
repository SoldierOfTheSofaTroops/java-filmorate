package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.request.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.PostFilmResponse;
import ru.yandex.practicum.filmorate.model.Film;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper {

    public static Film mapFromPostFilmRequestToFilm(PostFilmRequest postFilmRequest){
        Film film = new Film();
        film.setName(postFilmRequest.getName());
        film.setDescription(postFilmRequest.getDescription());
        film.setReleaseDate(postFilmRequest.getReleaseDate());
        film.setDuration(postFilmRequest.getDuration());
        film.setGenres(postFilmRequest.getGenres());
        film.setMpa(postFilmRequest.getMpa());
        return film;
    }

    public static PostFilmResponse mapFromFilmToPostFilmResponse(Film film){
        PostFilmResponse postFilmResponse = new PostFilmResponse();
        postFilmResponse.setId(film.getId());
        postFilmResponse.setName(film.getName());
        postFilmResponse.setDescription(film.getDescription());
        postFilmResponse.setReleaseDate(film.getReleaseDate());
        postFilmResponse.setDuration(film.getDuration());
        postFilmResponse.setGenres(film.getGenres());
        postFilmResponse.setMpa(film.getMpa());
        return postFilmResponse;
    }
}
