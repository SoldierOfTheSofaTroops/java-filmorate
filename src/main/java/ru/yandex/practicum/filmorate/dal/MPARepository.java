package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MPARepository {

    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mpaRowMapper;

    public MPARating getMpaById(long mpaId) {
        String query = "SELECT * FROM FILMORATE.MPA_RATING WHERE id = ?";

        if (!isExist(mpaId)) {
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

    private boolean isExist(long id){
        String idCountSql = "SELECT COUNT(id) FROM FILMORATE.MPA_RATING WHERE id = ?";
        return jdbcTemplate.queryForObject(idCountSql, Integer.class, id) > 0;
    }
}
