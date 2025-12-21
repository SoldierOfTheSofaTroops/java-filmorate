package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnNotValidException(){
        Film notValidfilm = new Film(0,
                "Some film",
                "Very intresting film",
                "1895-12-29",
                120);

        ResponseEntity<Film> notValidResponseEntity = restTemplate.postForEntity("/api/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }
}