package ru.yandex.practicum.filmorate.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnNotValidEmail() {
        User user = new User(0,
                "user_email",
                "User",
                "Denis",
                LocalDate.parse("1987-09-01"));

        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidLogin() {
        User user = new User(0,
                "user_email",
                "",
                "Denis",
                LocalDate.parse("1987-09-01"));

        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void loginSouldNotContainsBlanks() {
        User user = new User(0,
                "user_email",
                "qwerty qw",
                "Denis",
                LocalDate.parse("1987-09-01"));

        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidBirthday() {
        User user = new User(0,
                "user_email",
                "Test_login",
                "Denis",
                LocalDate.parse("2026-09-01"));

        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void userWithEmptyName() {
        User user = new User(
                "user@email",
                "Test_login",
                LocalDate.parse("1987-09-01"));

        ResponseEntity<User> validResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        Assert.assertNotNull(validResponseEntity.getBody());
        assertEquals("Test_login",validResponseEntity.getBody().getName());
        assertEquals(HttpStatus.CREATED, validResponseEntity.getStatusCode());
    }
}