package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dal.dto.FilmDTO;
import ru.yandex.practicum.filmorate.dal.dto.GenreDTO;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreDTO getGenreById(@Valid @PathVariable String id) {
        return genreService.getGenreById(id);
    }

    @GetMapping
    public Collection<GenreDTO> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/films/{id}")
    public FilmDTO getFilmsWithGenre(@PathVariable String id) {
        return genreService.getFilmsWithGenre(id);
    }
}
