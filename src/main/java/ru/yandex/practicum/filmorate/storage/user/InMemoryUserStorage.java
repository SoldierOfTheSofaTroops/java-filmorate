package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setId(getNextId());
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUserById(int id) {
        return users.get(id);
    }

    @Override
    public User deleteAllUsers() {
        return null;
    }

    @Override
    public User deleteUserById(int id) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            oldUser.setId(user.getId());
            oldUser.setName(user.getName());
            oldUser.setLogin(user.getLogin());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setEmail(user.getEmail());
            return user;
        }
        throw new NotFoundException("User not found");
    }

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
