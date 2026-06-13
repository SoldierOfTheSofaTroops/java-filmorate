package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.dal.dto.request.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dal.dto.response.CreateUserResponse;
import ru.yandex.practicum.filmorate.dal.dto.response.UpdateUserResponse;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User mapToUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setLogin(createUserRequest.getLogin());
        user.setName(createUserRequest.getName());
        user.setBirthday(LocalDate.parse(createUserRequest.getBirthday().toString()));
        return user;
    }

    public static CreateUserResponse mapToCreateUserResponse(User  user) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setId(user.getId());
        createUserResponse.setEmail(user.getEmail());
        createUserResponse.setLogin(user.getLogin());
        createUserResponse.setName(user.getName());
        createUserResponse.setBirthday(LocalDate.parse(user.getBirthday().toString()));
        return createUserResponse;
    }

    public static User mapToUserFromUpdateUserRequest(UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setId(updateUserRequest.getId());
        user.setEmail(updateUserRequest.getEmail());
        user.setLogin(updateUserRequest.getLogin());
        user.setName(updateUserRequest.getName());
        user.setBirthday(LocalDate.parse(updateUserRequest.getBirthday().toString()));
        return user;
    }

    public static UpdateUserResponse mapToUpdateUserResponse(User  user) {
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        updateUserResponse.setId(user.getId());
        updateUserResponse.setEmail(user.getEmail());
        updateUserResponse.setLogin(user.getLogin());
        updateUserResponse.setName(user.getName());
        updateUserResponse.setBirthday(user.getBirthday());
        return updateUserResponse;
    }
}
