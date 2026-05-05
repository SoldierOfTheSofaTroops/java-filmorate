package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDate;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    @Null (groups = CreateGroup.OnCreate.class)
    @NotNull (groups = UpdateGroup.OnUpdate.class)
    private Long id;

    @NotBlank (groups = CreateGroup.OnCreate.class)
    private String name;

    @Size(max = 200, groups = CreateGroup.OnCreate.class)
    private String description;

    @NotNull (groups = CreateGroup.OnCreate.class)
    @FilmReleaseDate (groups = {CreateGroup.OnCreate.class, UpdateGroup.OnUpdate.class})
    private LocalDate releaseDate;

    @NotNull (groups = CreateGroup.OnCreate.class)
    @Positive (groups = {CreateGroup.OnCreate.class, UpdateGroup.OnUpdate.class})
    private int duration;

    private long likes;
    private Set<Genre> genres = new HashSet<>();
    private Set<MPARating> mpaRatings = new HashSet<>();
    private Set<User> usersLikes =  new HashSet<>();
}
