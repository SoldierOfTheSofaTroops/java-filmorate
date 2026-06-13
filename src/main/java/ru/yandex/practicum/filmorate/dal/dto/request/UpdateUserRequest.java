package ru.yandex.practicum.filmorate.dal.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {

    @NotNull
    private Long id;

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
