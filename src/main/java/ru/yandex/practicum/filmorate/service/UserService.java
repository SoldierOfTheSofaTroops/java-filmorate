package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryUserStorage userStorage;

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void addToFriend(long whomId, long whoId) {
        if (userStorage.isUserExists(whomId) &&  userStorage.isUserExists(whoId)) {
            User user1 = userStorage.getUserById(whomId);
            User user2 = userStorage.getUserById(whoId);
            user1.getFriends().add(whoId);
            user2.getFriends().add(whomId);
            updateUser(user1);
            updateUser(user2);
        } else throw new NotFoundException("User not found");
    }

    public boolean removeFromFriend(long whomId, long whoId){
        if (userStorage.isUserExists(whomId) &&  userStorage.isUserExists(whoId)) {
            User whom = userStorage.getUserById(whomId);
            User who = userStorage.getUserById(whoId);
            if (whom.getFriends() == null) {
                whom.setFriends(new HashSet<>());
            }
            if (who.getFriends() == null) {
                who.setFriends(new HashSet<>());
            }
            whom.getFriends().remove(who.getId());
            who.getFriends().remove(whom.getId());
            updateUser(whom);
            updateUser(who);
            return true;
        }
        throw new NotFoundException("Something went wrong. One of the users may not have been found.");
    }

    public Collection<User> getUserFriends(long id) {
        return userStorage.getFriends(id);
    }

    public Collection<User> getCommonFriends(long firstUserId, long secondUserId) {
        if (userStorage.isUserExists(firstUserId) &&  userStorage.isUserExists(secondUserId)) {
            Set<Long> firstUserFriends = userStorage.getUserById(firstUserId).getFriends();
            Set<Long> secondUserFriends = userStorage.getUserById(secondUserId).getFriends();
            firstUserFriends.retainAll(secondUserFriends);
            return userStorage
                    .getAllUsers()
                    .stream()
                    .filter(user -> firstUserFriends.contains(user.getId())).toList();
        }
        throw new NotFoundException("User not found");
    }
}