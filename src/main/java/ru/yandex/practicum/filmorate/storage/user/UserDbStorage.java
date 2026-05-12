package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dal.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public class UserDbStorage implements UserStorage{

    JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean isUserExists(long userId) {
        return false;
    }

    @Override
    public Collection<User> getFriends(long id) {
        return List.of();
    }
}
