package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dal.dto.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.dal.dto.request.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.CreateUserResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.UpdateUserResponse;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User user = UserMapper.mapToUser(createUserRequest);
        return userRepository.createUser(user);
    }

    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = UserMapper.mapToUserFromUpdateUserRequest(updateUserRequest);
        return userRepository.updateUser(user);
    }

    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Collection<User> getFriends(String id) {
        return userRepository.getFriends(Long.parseLong(id));
    }


    public Collection<User> getCommonFriends(String first_User_id, String second_User_id) {

        long fistUserId = Long.parseLong(first_User_id);
        long secondUserId = Long.parseLong(second_User_id);
        if (fistUserId == secondUserId) {
            throw new IllegalArgumentException("The IDs of the first and second users should not be the same");
        }
        return userRepository.getCommonFriends(fistUserId, secondUserId);
    }

    public int addToFriend(long id, long friendId) {
        return userRepository.addToFriend(id, friendId);
    }

    public int removeFromFriend(long id, long friendId) {
        return userRepository.removeFromFriend(id, friendId);
    }
}