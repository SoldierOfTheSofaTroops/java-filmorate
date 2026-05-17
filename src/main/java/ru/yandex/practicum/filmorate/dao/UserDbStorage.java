package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Component
@Primary
public class UserDbStorage implements UserStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        jdbcTemplate
                .update("INSERT INTO filmorate.users(email, login, user_name, birthday) VALUES(?, ?, ?, ?)",
                        user.getEmail(),
                        user.getLogin(),
                        user.getName(),
                        user.getBirthday());
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        String sql = "SELECT * FROM filmorate.users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT * FROM filmorate.users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE FILMORATE.USERS SET email=?, login = ?, user_name=?, birthday = ? WHERE id = ?";

            jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId());

            return user;
    }

    @Override
    public boolean isUserExists(long userId) {
        String sql = "SELECT id FROM filmorate.users WHERE id = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), userId).isEmpty();
    }

    @Override
    public Collection<User> getFriends(long id) {
        return List.of();
    }
}
