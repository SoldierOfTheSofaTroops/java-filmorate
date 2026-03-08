package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;

    @Autowired
    private InMemoryUserStorage userStorage;

    public FilmService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Film postFilm(Film film) {
        return filmStorage.postFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
        }

    public Film addLike(long id, long userId) {
        if (userStorage.isUserExists(userId) && filmStorage.isFilmExists(id)) {
            Film film = filmStorage.getFilmById(id);
            User user = userStorage.getUserById(userId);
            film.setLikes(film.getLikes() + 1);
            film.getUsersLikes().add(user);
            filmStorage.updateFilm(film);
            return film;
        } else throw new NotFoundException("Film or user not found");
    }

    public Film removeLike(long id, long userId) {
        if (userStorage.isUserExists(userId) && filmStorage.isFilmExists(id)){
            Film film = filmStorage.getFilmById(id);
            User user = userStorage.getUserById(userId);
            if (film.getUsersLikes().contains(user)){
                film.setLikes(film.getLikes() - 1);
                film.getUsersLikes().remove(user);
                filmStorage.updateFilm(film);
            } else throw new NotFoundException("Users like not found");
            return film;
        } else throw new NotFoundException("Film or user not found");
    }

    public Collection<Film> getPopular(int count) {
        if (count > 0){
            Collection<Film> films = filmStorage.getFilms();
            return films.stream()
                    .sorted(Comparator.comparingLong(Film::getLikes).reversed())
                    .limit(count)
                    .collect(Collectors.toList());
        }
        throw new ConditionsNotMetException("Incorrect count value");
    }
}
