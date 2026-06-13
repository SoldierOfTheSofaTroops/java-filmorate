package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.dal.dto.UserDTO;
import ru.yandex.practicum.filmorate.dal.dto.response.CreateUserResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.UpdateUserResponse;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface AbstractUserRepository {

    CreateUserResponse createUser(User user);

    UpdateUserResponse updateUser(User user);

    Collection<User> getAllUsers();

    User findUserById(long id);


    boolean isUserExists(long id);

    Collection<User> getFriends(long id);

    Collection<User> getCommonFriends(long firstUserId, long secondUserId);

    int addToFriend(long id, long friendId);

    int removeFromFriend(long id, long friendId);
}
