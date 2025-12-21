package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController()
@RequestMapping(value = "/users")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @Validated @RequestBody User user) {
        log.info("Creating user {}", user);
        User newUser = new User();
        newUser.setId(getNextId());
        if (user.getName() == null || user.getName().isEmpty()) {
            newUser.setName(user.getLogin());
        } else {
            newUser.setName(user.getName());
        }
        newUser.setLogin(user.getLogin());
        newUser.setBirthday(user.getBirthday());
        newUser.setEmail(user.getEmail());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PutMapping
    public User updateUser(@Valid @Validated @RequestBody User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id is required");
        }
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

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
