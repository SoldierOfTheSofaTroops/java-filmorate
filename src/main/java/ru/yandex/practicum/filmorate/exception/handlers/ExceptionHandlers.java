package ru.yandex.practicum.filmorate.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;

import java.util.Map;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
public class ExceptionHandlers {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleFilmNotFound(final NotFoundException exception) {
        return Map.of(
                "error", "Not found",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler(ConditionsNotMetException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIncorrectCountValue(final ConditionsNotMetException exception) {
        return Map.of(
                "error", "Incorrect value",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotValidMethodArgument(final MethodArgumentNotValidException exception){
        return Map.of(
                "error", "Not valid argument",
                "errorMessage", exception.getMessage()
        );
    }
}
