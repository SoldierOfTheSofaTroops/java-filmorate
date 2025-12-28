package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    @NotBlank
    @Email(message = "Incorrect email address")
    private String email;
    @NotBlank (message = "Login field does not be empty")
    @Pattern(regexp = "^\\S+$", message = "Login doesn't contain whitespaces")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}