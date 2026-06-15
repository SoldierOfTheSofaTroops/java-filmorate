package ru.yandex.practicum.filmorate.dal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.time.LocalDate;
import java.util.List;

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
    private List<Genre> genres;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private MPARating mpa;
}
