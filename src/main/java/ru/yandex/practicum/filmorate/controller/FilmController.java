package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController()
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film postFilm(@Validated(CreateGroup.OnCreate.class) @RequestBody Film film) {
        return filmService.postFilm(film);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam (value = "count", required = false) Optional<Integer> count) {
        return filmService.getPopular(count.orElse(10));
    }

    @PutMapping
    public Film updadeFilm(@Validated(UpdateGroup.OnUpdate.class) @RequestBody Film film) {
       return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film setLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.removeLike(id, userId);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleFilmNotFound(final NotFoundException exception) {
        return Map.of(
                "error", "Film not found",
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
}
