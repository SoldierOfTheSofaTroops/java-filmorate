package ru.yandex.practicum.filmorate.exception.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}

