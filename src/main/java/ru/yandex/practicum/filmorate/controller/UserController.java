package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.request.user.post.CreateUserRequest;
import ru.yandex.practicum.filmorate.dal.dto.request.user.update.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.CreateUserResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.UpdateUserResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @PutMapping
    public UpdateUserResponse updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable String id) {
        return userService.getFriends(id);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public int addToFriend(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        return userService.addToFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public int deleteFriend(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        return userService.removeFromFriend(id, friendId);
    }
}
