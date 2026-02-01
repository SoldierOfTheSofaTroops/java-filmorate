package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
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

    public Set<Long> addToFriend(long id, long newFriendId){
        if (newFriendId >= 0
                && id >= 0
                && userStorage.isContainsUser(newFriendId)
                && userStorage.isContainsUser(id)) {
            User friend = getUserById(id);
            User newFriend = userStorage.getUserById(newFriendId);
            newFriend.getFriends().add(friend.getId());
            friend.getFriends().add(newFriend.getId());
            userStorage.updateUser(friend);
            userStorage.updateUser(newFriend);
            return friend.getFriends();
        }
        throw new NotFoundException("User not found");
    }
}
