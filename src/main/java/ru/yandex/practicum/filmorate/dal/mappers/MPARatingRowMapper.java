package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MPARatingRowMapper implements RowMapper<MPARating> {

    @Override
    public MPARating mapRow(ResultSet rs, int rowNum) throws SQLException {
        MPARating mpaRating = new MPARating();
        mpaRating.setId(rs.getInt("id"));
        mpaRating.setTitle(rs.getString("title"));
        return mpaRating;
    }
}
