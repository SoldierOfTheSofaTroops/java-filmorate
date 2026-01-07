package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = Username.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserNameValidator {
    String message() default "Duration must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}