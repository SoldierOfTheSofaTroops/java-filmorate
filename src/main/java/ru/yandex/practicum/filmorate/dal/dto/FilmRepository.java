package ru.yandex.practicum.filmorate.dal.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.dto.response.PostFilmResponse;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.storage.film.AbstractFilmRepository;

import java.util.*;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class FilmRepository implements AbstractFilmRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FilmRowMapper filmRowMapper;

    @Override
    public PostFilmResponse postFilm(Film film) {
        String insertFilmSql = "INSERT INTO FILMORATE.FILMS(film_name, description, release_date, duration) " +
                     "VALUES(?, ?, ?, ?)";
        String insertFilmGenreSql = "INSERT INTO FILMORATE.FILM_GENRE(film_id, genre_id) VALUES(?, ?)";
        String insertFilmMPARating = "INSERT INTO FILMORATE.FILM_MPA_RATING(film_id, film_mparating) VALUES(?, ?)";
        List<Genre> genres = film.getGenres();
        MPARating mpaRating = film.getMpa();

        int insertFilmResult = jdbcTemplate.update(insertFilmSql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());


        if (insertFilmResult < 1) {
            throw new  IllegalStateException("Film could not be inserted");
        }

        for (Genre genre : genres) {
            jdbcTemplate.update(insertFilmGenreSql, film.getId(), genre.getId());
        }

            jdbcTemplate.update(insertFilmMPARating, film.getId(), mpaRating.getId());

//        Optional<Film> lastInsertedFilm =
//                Optional.ofNullable(jdbcTemplate.queryForObject(insertFilmSql, filmRowMapper, film.getName()));

        return FilmMapper.mapFromFilmToPostFilmResponse(film);

    }

    @Override
    public Collection<Film> getFilms() {
        return List.of();
    }

    @Override
    public Film getFilmById(long id) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public boolean isFilmExists(long id) {
        String sql = "SELECT COUNT(*) FROM FILMORATE.FILMS WHERE id = ?";
        Optional<Integer> count = Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class));
        return count.get() > 0;
    }
}
