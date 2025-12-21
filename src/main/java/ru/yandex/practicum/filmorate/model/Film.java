package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDate;


@Data
@AllArgsConstructor
public class Film {

    private Integer id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @FilmReleaseDate
    private String releaseDate;
    @Positive
    private int duration;
}
