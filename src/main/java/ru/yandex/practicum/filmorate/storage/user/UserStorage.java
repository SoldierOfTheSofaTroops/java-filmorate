package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User createUser(User user);

    Collection<User> getAllUsers();

    User getUserById(int id);

    User deleteAllUsers();

    User deleteUserById(int id);

    User updateUser(User user);
}
