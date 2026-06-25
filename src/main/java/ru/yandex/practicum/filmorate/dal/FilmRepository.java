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
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.service.FilmService;
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
    private final UserRepository userRepository;
    private final FilmRowMapper filmRowMapper;
    private final MpaRowMapper mpaRowMapper;
    private final GenreRowMapper genreRowMapper;

    @Override
    public PostFilmResponse postFilm(Film film) {

        if (Optional.ofNullable(this.getMpaById(film.getMpa())).isEmpty()) {
            throw new NotFoundException("MPA with id " + film.getMpa() + " not found");
        }

        if (!film.getGenres().isEmpty()) {
            for(Long genreId : film.getGenres()){
                if (this.getGenreById(genreId) == null) {
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

    // =======================> MPA Repository <=======================

    public MPARating getMpaById(long mpaId) {
        String query = "SELECT * FROM FILMORATE.MPA_RATING WHERE id = ?";

        if (!isMpaExist(mpaId)) {
            throw new NotFoundException("MPA with id " + mpaId + " not found");
        }

        Optional<MPARating> mpaRating = Optional.ofNullable(jdbcTemplate.queryForObject(query, mpaRowMapper, mpaId));

        if (mpaRating.isPresent()) {
            return mpaRating.get();
        }

        throw new NotFoundException("MPA with id = " + mpaId + "not  found");
    }

    public Collection<MPARating> getAllMPA() {
        String query = "SELECT * FROM FILMORATE.MPA_RATING";
        return jdbcTemplate.query(query, mpaRowMapper);
    }

    private boolean isMpaExist(long id){
        String idCountSql = "SELECT COUNT(id) FROM FILMORATE.MPA_RATING WHERE id = ?";
        return jdbcTemplate.queryForObject(idCountSql, Integer.class, id) > 0;
    }

    // =======================> Genre Repository <=======================

    public Genre getGenreById(long id) {
        String query = "SELECT * FROM FILMORATE.GENRE WHERE id = ?";

        if (!isGenreExist(id)) {
            throw new NotFoundException("MPA with id " + id + " not found");
        }

        Optional<Genre> genre = Optional.ofNullable(jdbcTemplate.queryForObject(query, genreRowMapper, id));

        if (genre.isPresent()) {
            return genre.get();
        }

        throw new NotFoundException("Genre with id = " + id + "not  found");
    }

    public Collection<Genre> getAllGenres() {
        String query = "SELECT * FROM FILMORATE.GENRE";
        return jdbcTemplate.query(query, genreRowMapper);
    }

    public Film getFilmWithGenre(long filmId) {
        String sql = """
                SELECT f.ID,
                       f.FILM_NAME,
                       f.RELEASE_DATE,
                       f.DESCRIPTION,
                       f.DURATION,
                       fmpr.FILM_MPARATING,
                       fg.GENRE_ID
                       FROM FILMORATE.FILMS f
                INNER JOIN FILMORATE.FILM_MPA_RATING fmpr ON f.ID = fmpr.FILM_ID
                INNER JOIN FILMORATE.FILM_GENRE fg on f.ID = FG.FILM_ID
                WHERE ID=?""";

        if (this.isFilmExists(filmId)) {
            return jdbcTemplate.queryForObject(sql, filmRowMapper, filmId);
        }

        throw new NotFoundException("Genre with id = " + filmId + " not found");
    }

    private boolean isGenreExist(long id){
        String idCountSql = "SELECT COUNT(id) FROM FILMORATE.GENRE WHERE id = ?";
        return jdbcTemplate.queryForObject(idCountSql, Integer.class, id) > 0;
    }
}
