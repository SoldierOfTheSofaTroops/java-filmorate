package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Long, User> users = new HashMap<>();

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
    public User getUserById(long id) {
        return users.get(id);
    }

    @Override
    public Map<Long, User> deleteAllUsers() {
        users.clear();
        return users;
    }

    @Override
    public User deleteUserById(long id) {
        return users.remove(id);
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

    public boolean isUserExists(long id) {
        return users.containsKey(id);
    }

    public Collection<User> getFriends(long id) {
        if (users.containsKey(id)) {
            Set<Long> friends = users.get(id).getFriends();
            return friends.stream()
                    .map(f -> users.get(f))
                    .collect(Collectors.toList());
        }
        throw new NotFoundException("User not found");
    }

    private long getNextId() {
        int currentMaxId = Math.toIntExact(users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0));
        return ++currentMaxId;
    }
}
