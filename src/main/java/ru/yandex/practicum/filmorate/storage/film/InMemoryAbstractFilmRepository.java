package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.dto.response.PostFilmResponse;
import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryAbstractFilmRepository implements AbstractFilmRepository {

    private Map<Long, Film> films = new HashMap<>();

    public PostFilmResponse postFilm(Film film) {
//        film.setId(getNextId());
//        films.put(film.getId(), film);
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            Film oldFilm = films.get(film.getId());
            oldFilm.setId(film.getId());
            oldFilm.setName(film.getName());
            oldFilm.setDescription(film.getDescription());
            oldFilm.setReleaseDate(film.getReleaseDate());
            oldFilm.setDuration(film.getDuration());
            films.put(oldFilm.getId(), oldFilm);
            return oldFilm;
        } else throw new NotFoundException("Film not found");
    }

    @Override
    public int addLike(long filmId, long userId) {
        return 0;
    }

    @Override
    public int removeLike(long filmId, long userId) {
        return 0;
    }

    public boolean isFilmExists(long id) {
        return films.containsKey(id);
    }

    public Film getFilmById(long id) {
        return films.get(id);
    }

    private long getNextId() {
        int currentMaxId = Math.toIntExact(films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0));
        return ++currentMaxId;
    }
}
