package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {

    User createUser(User user);

    Collection<User> getAllUsers();

    User getUserById(long id);

    Map<Long, User> deleteAllUsers();

    User deleteUserById(long id);

    User updateUser(User user);
}
