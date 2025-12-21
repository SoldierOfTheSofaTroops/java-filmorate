package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PastOrPresentStringValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PastOrPresentValidator {
    String message() default "Birthday must be past or present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
