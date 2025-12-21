package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;

import ru.yandex.practicum.filmorate.validation.PastOrPresentValidator;

@Data
@NoArgsConstructor
public class User {

    private Integer id;
    @NotBlank
    @Email(message = "Incorrect email address")
    private String email;
    @NotBlank (message = "Login field does not be empty")
    @Pattern(regexp = "^\\S+$", message = "Login doesn't contain whitespaces")
    private String login;
    private String name;
    @PastOrPresentValidator
    private String birthday;
}