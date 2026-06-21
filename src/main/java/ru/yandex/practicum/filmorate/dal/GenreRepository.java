package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepository {

    private final JdbcTemplate jdbcTemplate;
    private final GenreRowMapper genreRowMapper;
    private final FilmRowMapper filmRowMapper;
    private final FilmRepository filmRepository;

    public Genre getGenreById(long id) {
        String query = "SELECT * FROM FILMORATE.GENRE WHERE id = ?";

        if (!isExist(id)) {
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
                       FROM FILMS f
                INNER JOIN FILM_MPA_RATING fmpr ON f.ID = fmpr.FILM_ID
                INNER JOIN FILMORATE.FILM_GENRE fg on f.ID = FG.FILM_ID
                WHERE ID=?""";

        if (filmRepository.isFilmExists(filmId)) {
            return jdbcTemplate.queryForObject(sql, filmRowMapper, filmId);
        }

        throw new NotFoundException("Genre with id = " + filmId + " not found");
    }

    private boolean isExist(long id){
        String idCountSql = "SELECT COUNT(id) FROM FILMORATE.GENRE WHERE id = ?";
        return jdbcTemplate.queryForObject(idCountSql, Integer.class, id) > 0;
    }
}
