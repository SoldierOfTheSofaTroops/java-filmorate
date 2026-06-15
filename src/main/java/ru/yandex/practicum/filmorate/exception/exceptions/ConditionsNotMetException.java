package ru.yandex.practicum.filmorate.exception.exceptions;

public class ConditionsNotMetException extends RuntimeException {
    public ConditionsNotMetException(final String message) {
        super(message);
    }
}
