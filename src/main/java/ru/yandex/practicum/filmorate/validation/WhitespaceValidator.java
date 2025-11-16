package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.exception.ValidationException;

public class WhitespaceValidator implements ConstraintValidator<Whitespace, String> {
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (string == null) throw new ValidationException("string is null");
        return !string.isBlank() && !string.contains(" ");
    }
}
