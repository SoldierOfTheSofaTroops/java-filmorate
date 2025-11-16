package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpRequestFilmControllerTest {

    @BeforeAll
    public static void setup() throws JSONException {
        String createUserUrl = "http://localhost:8080/users/new-user";
        String updateUserUrl = "http://localhost:8080/users/update";
        String getAllUsersUrl = "http://localhost:8080/all";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 0);
        jsonObject.put("email", "email@email.com");
        jsonObject.put("login", "Test user");
        jsonObject.put("name", "Test name");
        jsonObject.put("birthday", "2020-01-01");
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldPostNewFilm() throws Exception {
        this.restTemplate.postForObject("http://localhost:" + port + "/films/new", null, String.class);
    }
}
