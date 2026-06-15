package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.request.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.PostFilmResponse;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostFilmResponse postFilm(@Valid @RequestBody PostFilmRequest postFilmRequest) {
        return filmService.postFilm(postFilmRequest);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam (value = "count", required = false, defaultValue = "10") Integer count) {
        return filmService.getPopular(count);
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
}
