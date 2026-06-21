package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.dto.response.film.post.PostFilmResponse;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.film.AbstractFilmRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class FilmRepository implements AbstractFilmRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FilmRowMapper filmRowMapper;
    private final UserRepository userRepository;
    private final MPARepository mpaRepository;
    private final GenreService genreService;

    @Override
    public PostFilmResponse postFilm(Film film) {

        if (Optional.ofNullable(mpaRepository.getMpaById(film.getMpa())).isEmpty()) {
            throw new NotFoundException("MPA with id " + film.getMpa() + " not found");
        }

        if (!film.getGenres().isEmpty()) {
            for(Long genreId : film.getGenres()){
                if (genreService.getGenreById(String.valueOf(genreId)) == null) {
                    throw new NotFoundException("Genre with id " + genreId + " not found");
                }
            }
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        List<Long> genres = film.getGenres();
        Long mpaRating = film.getMpa();

        String insertFilmSql = "INSERT INTO FILMORATE.FILMS(film_name, description, release_date, duration) VALUES(?, ?, ?, ?)";
        String insertFilmGenreSql = "INSERT INTO FILMORATE.FILM_GENRE(film_id, genre_id) VALUES(?, ?)";
        String insertFilmMPARating = "INSERT INTO FILMORATE.FILM_MPA_RATING(film_id, film_mparating) VALUES(?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement =
                    con.prepareStatement(insertFilmSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setObject(3, film.getReleaseDate());
            preparedStatement.setInt(4, film.getDuration());
            return preparedStatement;}, keyHolder);

        Long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        for (Long genreId : genres) {
            jdbcTemplate.update(insertFilmGenreSql, filmId, genreId);
        }

        jdbcTemplate.update(insertFilmMPARating, filmId, mpaRating);

        film.setId(filmId);

        return FilmMapper.mapFromFilmToPostFilmResponse(film);
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "SELECT * FROM FILMORATE.FILMS";
        return jdbcTemplate.query(sql, filmRowMapper);
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
    public int addLike(long filmId, long userId) {
        String addLikeSql = "INSERT INTO FILMORATE.USERS_FILMS_LIKES(film_id, user_id) VALUES(?, ?)";

        if (isFilmExists(filmId) && userRepository.isUserExists(userId)) {
            return jdbcTemplate.update(addLikeSql, filmId, userId);
        }

        throw new NotFoundException("User or film not found");
    }

    @Override
    public int removeLike(long filmId, long userId) {
        String removeLikeSql = "DELETE FROM FILMORATE.USERS_FILMS_LIKES WHERE film_id = ? AND user_id = ?";

        if (isFilmExists(filmId) && userRepository.isUserExists(userId)) {
            return jdbcTemplate.update(removeLikeSql, filmId, userId);
        }

        throw new NotFoundException("User or film not found");
    }

    @Override
    public boolean isFilmExists(long id) {
        String sql = "SELECT COUNT(*) FROM FILMORATE.FILMS WHERE id = ?";
        Optional<Integer> count = Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class));
        return count.get() > 0;
    }
}
