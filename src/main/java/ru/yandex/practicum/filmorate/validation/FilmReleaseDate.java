package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = FilmReleaseDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilmReleaseDate {
    String message() default "Date must be not before December 28, 1895";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
