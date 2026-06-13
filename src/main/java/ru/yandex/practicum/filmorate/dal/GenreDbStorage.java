package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public Collection<Film> getFilmsWithGenre() {
        return List.of();
    }

    public Genre getGenreById(long id) {
        String sql = "SELECT * FROM FILMORATE.GENRE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GenreMapper(), id);
    }
}
