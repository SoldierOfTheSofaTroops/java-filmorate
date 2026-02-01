package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        userService.createUser(user);
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getAllUsers();
    }

    @PutMapping
    public User updateUser(@Validated(UpdateGroup.OnUpdate.class) @RequestBody User user) {
        return userService.updateUser(user);
    }
}
