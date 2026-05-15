package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
@Primary
public class UserDbStorage implements UserStorage{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int createUser(User user) {
        return jdbcTemplate
                .update("INSERT INTO filmorate.users VALUES(?, ?, ?, ?)",
                        user.getEmail(),
                        user.getLogin(),
                        user.getName(),
                        user.getBirthday());
    }

    @Override
    public Collection<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT * FROM filmorate.users WHERE id = ?";
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
