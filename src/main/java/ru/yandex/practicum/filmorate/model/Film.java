package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDate;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class Film {

    @Null(groups = )
    private Integer id;
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
}
