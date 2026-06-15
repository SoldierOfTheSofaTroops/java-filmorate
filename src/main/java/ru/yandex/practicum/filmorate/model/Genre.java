package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

@Data
public class Genre {

    private long id;

    @NotBlank
    private String title;
}
