package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

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
        return null;
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
        return userStorage.updateUser(user);
    }

    public User addToFriend(User user, long newFriendId){
        if (newFriendId > 0
                && userStorage.isContainsUser(newFriendId)
                && userStorage.isContainsUser(user.getId())) {
            User newFriend = userStorage.getUserById(newFriendId);
            newFriend.getFriends().add(user.getId());
            user.getFriends().add(newFriendId);
            userStorage.updateUser(user);
            userStorage.updateUser(newFriend);
            return user;
        }
        throw new NotFoundException("User not found");
    }
}
