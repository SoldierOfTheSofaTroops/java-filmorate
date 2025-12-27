package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnNotValidReleaseDate(){
        Film notValidfilm = new Film(0,
                "Some film",
                "Very intresting film",
                "1895-12-28",
                120);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidFilmName(){
        Film notValidfilm = new Film(0,
                "",
                "Very intresting film",
                "1895-12-29",
                120);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidDuration(){
        Film notValidfilm = new Film(0,
                "Some film",
                "Very intresting film",
                "1895-12-29",
                -1);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidDescriptionLength(){
        Film notValidfilm = new Film(0,
                "Some film",
                "Veryintrestingfilmwkjbcweihbvciwhebvwhie" +
                        "bvwiebcvkjsdhbciwehbvchkjbvhrebviwbewkjbfe" +
                        "kwhvbrbvkhjwbvwhevbwkjvwhjbevwhievbwhjebjh" +
                        "cbwiebkjhcbwjkehbcwiekkjbcwiebchwjebcwihbew" +
                        "jhbckjwehcbwibvcjhgdcjhbdkhjvbkjabvhjsbvfhj" +
                        "sbvhjgsebakvbjksgv",
                "1895-12-29",
                120);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnFilmCreated(){
        Film validFilm = new Film(0,
                "Some film",
                "Very intresting film",
                "1895-12-29",
                120);
        ResponseEntity<Film> validFilmResp = testRestTemplate.postForEntity("http://localhost:" + port + "/films", validFilm, Film.class);
        assertEquals(HttpStatus.CREATED, validFilmResp.getStatusCode());
    }
}