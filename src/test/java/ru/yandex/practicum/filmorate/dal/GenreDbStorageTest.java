package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor
public class GenreDbStorageTest {
    private final GenreDbStorage genreDbStorage;

    @Test
    public void getGenreByIdTest() {

        Genre genre = new  Genre();
        genre.setId(1);
        genre.setTitle("Комедия");

        Optional<Genre> genreFromDb = Optional.ofNullable(genreDbStorage.getGenreById(1L));

        assertThat(genreFromDb).isPresent().get().isEqualTo(genre);
    }
}
