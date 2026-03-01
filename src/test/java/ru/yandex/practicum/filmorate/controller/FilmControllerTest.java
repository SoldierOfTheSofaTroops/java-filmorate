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

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnNotValidReleaseDate() {
        Film notValidfilm = new Film();
        notValidfilm.setId(0L);
        notValidfilm.setName("Some film");
        notValidfilm.setDescription("Very intresting film");
        notValidfilm.setReleaseDate(LocalDate.parse("1895-12-28"));
        notValidfilm.setDuration(120);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidFilmName() {
        Film notValidfilm = new Film();
        notValidfilm.setId(0L);
        notValidfilm.setName("");
        notValidfilm.setDescription("Very intresting film");
        notValidfilm.setReleaseDate(LocalDate.parse("1895-12-28"));
        notValidfilm.setDuration(120);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidDuration() {
        Film notValidfilm = new Film();
        notValidfilm.setId(0L);
        notValidfilm.setName("Some film");
        notValidfilm.setDescription("Very intresting film");
        notValidfilm.setReleaseDate(LocalDate.parse("1895-12-28"));
        notValidfilm.setDuration(-1);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotValidDescriptionLength() {
        Film notValidfilm = new Film();
        notValidfilm.setId(0L);
        notValidfilm.setName("Some film");
        notValidfilm.setDescription("Veryintrestingfilmwkjbcweihbvciwhebvwhie" +
                "bvwiebcvkjsdhbciwehbvchkjbvhrebviwbewkjbfe" +
                "kwhvbrbvkhjwbvwhevbwkjvwhjbevwhievbwhjebjh" +
                "cbwiebkjhcbwjkehbcwiekkjbcwiebchwjebcwihbew" +
                "jhbckjwehcbwibvcjhgdcjhbdkhjvbkjabvhjsbvfhj" +
                "sbvhjgsebakvbjksgv");
        notValidfilm.setReleaseDate(LocalDate.parse("1895-12-28"));
        notValidfilm.setDuration(120);
        ResponseEntity<Film> notValidResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/films", notValidfilm, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, notValidResponseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnFilmCreated() {
        Film validFilm = new Film();
        validFilm.setName("Some film");
        validFilm.setDescription("Very intresting film");
        validFilm.setReleaseDate(LocalDate.parse("1900-12-28"));
        validFilm.setDuration(120);
        ResponseEntity<Film> validFilmResp = testRestTemplate.postForEntity("http://localhost:" + port + "/films", validFilm, Film.class);
        assertEquals(HttpStatus.CREATED, validFilmResp.getStatusCode());
    }
}