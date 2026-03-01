package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.time.LocalDate;
import java.util.*;

@Data
public class User {

    @Null (groups = CreateGroup.OnCreate.class)
    private Long id;

    @NotBlank (groups = CreateGroup.OnCreate.class)
    @Email(message = "Incorrect email address", groups = {CreateGroup.OnCreate.class, UpdateGroup.OnUpdate.class})
    private String email;

    @NotBlank (message = "Login field does not be empty", groups = CreateGroup.OnCreate.class)
    @Pattern(regexp = "^\\S+$", message = "Login doesn't contain whitespaces", groups = {CreateGroup.OnCreate.class, UpdateGroup.OnUpdate.class})
    private String login;

    private String name;

    @PastOrPresent (groups = {CreateGroup.OnCreate.class,  UpdateGroup.OnUpdate.class})
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();
}