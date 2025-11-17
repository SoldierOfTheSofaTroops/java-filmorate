package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController()
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film postFilm(@Valid @RequestBody Film film) {
        Film newFilm = new Film();
        newFilm.setId(getNextId());
        newFilm.setName(film.getName());
        newFilm.setDescription(film.getDescription());
        newFilm.setReleaseDate(film.getReleaseDate());
        newFilm.setDuration(film.getDuration());
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updadeFilm(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            throw new ConditionsNotMetException("Id is required");
        }
        if (films.containsKey(film.getId())) {
            Film oldFilm = films.get(film.getId());
            oldFilm.setName(film.getName());
            oldFilm.setDescription(film.getDescription());
            oldFilm.setReleaseDate(film.getReleaseDate());
            oldFilm.setDuration(film.getDuration());
            films.put(oldFilm.getId(), oldFilm);
            return oldFilm;
        }
        throw new NotFoundException("Film not found");
    }

    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
