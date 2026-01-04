package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Null (groups = CreateGroup.onCreate.class)
    private Integer id;

    @NotBlank (groups = CreateGroup.onCreate.class)
    @Email(message = "Incorrect email address", groups = {CreateGroup.onCreate.class, UpdateGroup.onUpdate.class})
    private String email;

    @NotBlank (message = "Login field does not be empty", groups = CreateGroup.onCreate.class)
    @Pattern(regexp = "^\\S+$", message = "Login doesn't contain whitespaces", groups = {CreateGroup.onCreate.class, UpdateGroup.onUpdate.class})
    private String login;

    private String name;

    @PastOrPresent (groups = {CreateGroup.onCreate.class,  UpdateGroup.onUpdate.class})
    private LocalDate birthday;
}