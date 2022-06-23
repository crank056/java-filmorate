package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class FilmService {

    FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage){
        this.filmStorage = filmStorage;
    }

    public boolean addLike(long userId, long filmId) {
        filmStorage.getAllFilms().get(filmId).getLikes().add(userId);
        return filmStorage.getAllFilms().get(filmId).getLikes().contains(userId);
    }

    public boolean deleteLike(long userId, long filmId) {
        filmStorage.getAllFilms().get(filmId).getLikes().remove(userId);
        return filmStorage.getAllFilms().get(filmId).getLikes().contains(userId);
    }

    public long getLikes(long filmId) {
        return filmStorage.getAllFilms().get(filmId).getLikes().size() + 1;
    }

    public ArrayList<Film> getBestFilms() {
        ArrayList<Film> bestFilms = new ArrayList<>();
        TreeSet<Film> sortedFilms = new TreeSet<>();
        for(Film film: filmStorage.getAllFilms().values()) {
            sortedFilms.add(film);
        }
        for(int i = 0; i < 10; i++) {
            Film film = sortedFilms.first();
            bestFilms.add(film);
            sortedFilms.remove(film);
        }
        return bestFilms;
    }
}
