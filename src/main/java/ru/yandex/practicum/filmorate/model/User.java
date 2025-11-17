package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.yandex.practicum.filmorate.validation.PastOrPresent;
import ru.yandex.practicum.filmorate.validation.Whitespace;

@Data
@NoArgsConstructor
public class User {

    private Integer id;
    @NotBlank
    @Email(message = "Incorrect email address")
    private String email;
    @NotBlank (message = "Login field does not be empty")
    @Whitespace (message = "Field don't contain whitespace")
    private String login;
    private String name;
    @PastOrPresent
    private String birthday;
}