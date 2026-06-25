package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.FilmDTO;
import ru.yandex.practicum.filmorate.dal.dto.GenreDTO;
import ru.yandex.practicum.filmorate.dal.dto.MpaDTO;
import ru.yandex.practicum.filmorate.dal.dto.request.film.post.PostFilmRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.PostFilmResponse;
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

    // ==================> Film controller <==================

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

    @PutMapping("/{filmId}/like/{userId}")
    public int addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return filmService.removeLike(filmId, userId);
    }

    // ==================> MPA controller <==================

    @GetMapping
    public Collection<MpaDTO> getAllMPA(){
        return filmService.getAllMPA();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public MpaDTO getMpaById(@PathVariable String id){
        return filmService.getMPAById(id);
    }

    // ==================> Genre controller <==================

    @GetMapping("/{id}")
    public GenreDTO getGenreById(@Valid @PathVariable String id) {
        return filmService.getGenreById(id);
    }

    @GetMapping
    public Collection<GenreDTO> getGenres() {
        return filmService.getGenres();
    }

    @GetMapping("/films/{id}")
    public FilmDTO getFilmsWithGenre(@PathVariable String id) {
        return filmService.getFilmsWithGenre(id);
    }
}
