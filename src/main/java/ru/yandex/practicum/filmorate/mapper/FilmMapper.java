package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.request.film.post.GenreFilmPostRequest;
import ru.yandex.practicum.filmorate.dal.dto.request.film.post.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.GenrePostFilmResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.MPAFilmPostResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.PostFilmResponse;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper {

    public static Film mapFromPostFilmRequestToFilm(PostFilmRequest postFilmRequest){
        Film film = new Film();
        film.setName(postFilmRequest.getName());
        film.setDescription(postFilmRequest.getDescription());
        film.setReleaseDate(postFilmRequest.getReleaseDate());
        film.setDuration(postFilmRequest.getDuration());
        if (postFilmRequest.getGenres() != null) {
            film.setGenres(postFilmRequest
                    .getGenres()
                    .stream()
                    .map(GenreFilmPostRequest::getId)
                    .collect(Collectors.toList()));
        }
        film.setMpa(postFilmRequest.getMpa().getId());
        return film;
    }

    public static PostFilmResponse mapFromFilmToPostFilmResponse(Film film){
        PostFilmResponse postFilmResponse = new PostFilmResponse();
        postFilmResponse.setId(film.getId());
        postFilmResponse.setName(film.getName());
        postFilmResponse.setDescription(film.getDescription());
        postFilmResponse.setReleaseDate(film.getReleaseDate());
        postFilmResponse.setDuration(film.getDuration());
        postFilmResponse.setGenres(film.getGenres()
                .stream()
                .map(GenrePostFilmResponse::new)
                .toList());
        postFilmResponse.setMpa(new MPAFilmPostResponse(film.getMpa()));
        return postFilmResponse;
    }
}
