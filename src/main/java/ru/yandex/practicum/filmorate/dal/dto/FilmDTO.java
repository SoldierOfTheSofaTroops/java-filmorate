package ru.yandex.practicum.filmorate.dal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FilmDTO {

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
    private Long mpa;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> genres;
}
