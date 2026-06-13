package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    public Collection<Film> getFilmsWithGenre(){
        return genreDbStorage.getFilmsWithGenre();
    }

    public Genre getGenreById(String id) {
        return genreDbStorage.getGenreById(Long.parseLong(id));
    }
}
