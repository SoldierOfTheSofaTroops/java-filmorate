package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDate;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Null (groups = CreateGroup.onCreate.class)
    @NotNull (groups = UpdateGroup.onUpdate.class)
    private Integer id;

    @NotBlank (groups = CreateGroup.onCreate.class)
    private String name;

    @Size(max = 200, groups = CreateGroup.onCreate.class)
    private String description;

    @NotNull (groups = CreateGroup.onCreate.class)
    @FilmReleaseDate (groups = {CreateGroup.onCreate.class, UpdateGroup.onUpdate.class})
    private LocalDate releaseDate;

    @NotNull (groups = CreateGroup.onCreate.class)
    @Positive (groups =  {CreateGroup.onCreate.class, UpdateGroup.onUpdate.class})
    private int duration;
}
