package ru.yandex.practicum.filmorate.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    InMemoryUserStorage inMemoryUserStorage;

    @Test
    public void shouldReturnNotValidEmail() {
        User user = new User();
        user.setEmail("user_email");
        user.setLogin("user_login");
        user.setName("User");
        user.setBirthday(LocalDate.parse("1987-09-01"));
        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidLogin() {
        User user = new User();
        user.setEmail("user_email");
        user.setLogin("");
        user.setName("User");
        user.setBirthday(LocalDate.parse("1987-09-01"));
        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void loginSouldNotContainsBlanks() {
        User user = new User();
        user.setEmail("user_email");
        user.setLogin("user login");
        user.setName("User");
        user.setBirthday(LocalDate.parse("1987-09-01"));
        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidBirthday() {
        User user = new User();
        user.setEmail("user_email");
        user.setLogin("user_login");
        user.setName("User");
        user.setBirthday(LocalDate.parse("2027-09-01"));
        ResponseEntity<User> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void userWithEmptyName() {
        User user = new User();
        user.setLogin("user_login");
        user.setEmail("dkcreator@gmail.com");
        user.setBirthday(LocalDate.parse("1987-09-01"));
        ResponseEntity<User> validResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/users", user, User.class);
        Assert.assertNotNull(validResponseEntity.getBody());
        assertEquals("user_login",validResponseEntity.getBody().getName());
        assertEquals(HttpStatus.CREATED, validResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnCommonFriends() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin("user1");
        user2.setLogin("user2");
        user3.setLogin("user3");
        user1.setBirthday(LocalDate.parse("1987-09-01"));
        user2.setBirthday(LocalDate.parse("1987-09-01"));
        user3.setBirthday(LocalDate.parse("1987-09-01"));
        user1.setEmail("user1@mail.com");
        user2.setEmail("user2@mail.com");
        user3.setEmail("user3@mail.com");
        ResponseEntity<User> responseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/users", user1, User.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("user1", responseEntity.getBody().getName());

        responseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/users", user2, User.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("user2", responseEntity.getBody().getName());

        responseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/users", user3, User.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("user3", responseEntity.getBody().getName());

        testRestTemplate.put("http://localhost:" + port + "/users/1/friends/2", User.class);
        testRestTemplate.put("http://localhost:" + port + "/users/1/friends/3", User.class);

        ResponseEntity<HashSet<User>> response =
                testRestTemplate.exchange("http://localhost:" + port + "/users/2/friends/common/3",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        HashSet<User> users = response.getBody();
        User user = users.stream().findFirst().get();
        assertEquals(inMemoryUserStorage.getUserById(1), user);
        assertEquals(user1.getEmail(), user.getEmail());
        assertEquals(user1.getLogin(), user.getLogin());
    }
}