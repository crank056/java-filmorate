package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage){
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public boolean addLike(long userId, long filmId) throws WrongIdException {
        if(filmStorage.getAllFilms().get(filmId) != null && userStorage.getAllUsers().get(userId) != null) {
            filmStorage.getAllFilms().get(filmId).getLikes().add(userId);
        } else throw new WrongIdException("Неверный id");
        return filmStorage.getAllFilms().get(filmId).getLikes().contains(userId);
    }

    public boolean deleteLike(long userId, long filmId) throws WrongIdException {
        if(filmStorage.getAllFilms().get(filmId) != null && userStorage.getAllUsers().get(userId) != null) {
            filmStorage.getAllFilms().get(filmId).getLikes().remove(userId);
        } else throw new WrongIdException("Неверный id");
        return filmStorage.getAllFilms().get(filmId).getLikes().contains(userId);
    }

    public long getLikes(long filmId) {
        return filmStorage.getAllFilms().get(filmId).getLikes().size() + 1;
    }

    public ArrayList<Film> getBestFilms(int count) {
        ArrayList<Film> bestFilms = new ArrayList<>();
        TreeSet<Film> sortedFilms = new TreeSet<>();
        for(Film film: filmStorage.getAllFilms().values()) {
            sortedFilms.add(film);
        }
        for(int i = 0; i < count && i < filmStorage.getAllFilms().size(); i++) {
                Film film = sortedFilms.first();
                bestFilms.add(film);
                sortedFilms.remove(film);
        }
        return bestFilms;
    }
}
