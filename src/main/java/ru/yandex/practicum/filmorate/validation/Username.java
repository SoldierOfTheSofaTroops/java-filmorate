package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.model.User;

public class Username implements ConstraintValidator<UserNameValidator, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return true;
    }
}
