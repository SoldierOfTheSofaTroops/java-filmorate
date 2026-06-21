package ru.yandex.practicum.filmorate.dal.dto.response.film.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.yandex.practicum.filmorate.dal.dto.request.film.post.MPAFilmPostRequest;

import java.time.LocalDate;

import java.util.Collection;

@Data
public class PostFilmResponse {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate releaseDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int duration;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Collection<GenrePostFilmResponse> genres;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private MPAFilmPostResponse mpa;
}
