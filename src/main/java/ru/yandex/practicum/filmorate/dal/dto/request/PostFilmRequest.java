package ru.yandex.practicum.filmorate.dal.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDate;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostFilmRequest {
    @Null
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    @FilmReleaseDate
    private LocalDate releaseDate;
    @NotNull
    @Positive
    private int duration;
    private MPARating mpa;
    private List<Genre> genres;
}
