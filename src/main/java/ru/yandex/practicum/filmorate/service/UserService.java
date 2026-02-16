package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserStorage {

    private final InMemoryUserStorage userStorage;

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public Map<Long, User> deleteAllUsers() {
        return userStorage.deleteAllUsers();
    }

    @Override
    public User deleteUserById(long id) {
        return userStorage.deleteUserById(id);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public Set<Long> addToFriend(long whomId, long whoId){
        if (whoId >= 0
                && whomId >= 0
                && userStorage.isUserExists(whoId)
                && userStorage.isUserExists(whomId)) {
            User whom = getUserById(whomId);
            User who = getUserById(whoId);
            who.getFriends().add(whom.getId());
            whom.getFriends().add(who.getId());
            userStorage.updateUser(whom);
            userStorage.updateUser(who);
            return whom.getFriends();
        }
        throw new NotFoundException("User not found");
    }
}
