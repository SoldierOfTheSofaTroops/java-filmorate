package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.time.LocalDate;

@Data
public class User {

    private Long id;

//    @NotBlank (groups = CreateGroup.class)
//    @Email(message = "Incorrect email address", groups = {CreateGroup.class, UpdateGroup.OnUpdate.class})
//    @Email(message = "Incorrect email address", groups = {CreateGroup.class, UpdateGroup.OnUpdate.class})
    private String email;

//    @NotBlank (message = "Login field does not be empty", groups = CreateGroup.class)
//    @Pattern(regexp = "^\\S+$", message = "Login doesn't contain whitespaces", groups = {CreateGroup.class, UpdateGroup.OnUpdate.class})
    private String login;

    private String name;

    @PastOrPresent (groups = {CreateGroup.class,  UpdateGroup.OnUpdate.class})
    private LocalDate birthday;
}