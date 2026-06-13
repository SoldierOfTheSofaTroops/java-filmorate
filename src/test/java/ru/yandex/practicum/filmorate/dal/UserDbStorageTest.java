package ru.yandex.practicum.filmorate.dal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
public class UserDbStorageTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User newTestUser = new User();
        newTestUser.setEmail("newTestUser@email.com");
        newTestUser.setLogin("newTestUserLogin");
        newTestUser.setName("newTestUserName");
        newTestUser.setBirthday(LocalDate.parse("1987-09-01"));

        //доработать проверку результата
    }

    @Test
    public void testGetAllUsers() {
        Collection<User> users = userRepository.getAllUsers();

        assertThat(users).isNotNull();
    }

    @Test
    public void testUpdateUser() {
        Optional<User> testUser = Optional.ofNullable(userRepository.findUserById(1L));

        testUser.ifPresent(user -> {
            user.setLogin("newTestUser");
            user.setEmail("testEmail@test.com");
        });

        userRepository.updateUser(testUser.get());

        assertThat(testUser.get()).isEqualTo(userRepository.findUserById(1L));
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );
    }
}