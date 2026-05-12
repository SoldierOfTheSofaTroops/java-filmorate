package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validation.groups.CreateGroup;
import ru.yandex.practicum.filmorate.validation.groups.UpdateGroup;

import java.util.Collection;

@Slf4j
@RestController()
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Validated(CreateGroup.OnCreate.class)
                           @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable @RequestParam long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable String id) {
        return userService.getUserFriends(Long.parseLong(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getCommonFriends(Long.parseLong(id), Long.parseLong(otherId));
    }

    @PutMapping
    public User updateUser(@Validated(UpdateGroup.OnUpdate.class) @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return updatedUser;
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addToFriend(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        userService.addToFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public boolean deleteFriend(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        return userService.removeFromFriend(id, friendId);
    }
}
