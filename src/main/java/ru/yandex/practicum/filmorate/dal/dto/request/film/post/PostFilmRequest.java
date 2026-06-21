package ru.yandex.practicum.filmorate.dal.dto.request.film.post;

import jakarta.validation.constraints.*;
import lombok.Data;
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
    private MPAFilmPostRequest mpa;
    private List<GenreFilmPostRequest> genres;
}
