package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthdayValidation implements ConstraintValidator<PastOrPresent, String> {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !LocalDate.from(dateTimeFormatter.parse(string)).isAfter(LocalDate.now());
    }
}
