package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;


import java.util.*;

@Service
public class FilmService {
    private FilmDbStorage filmDbStorage;
    private InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(FilmDbStorage filmDbStorage, InMemoryUserStorage inMemoryUserStorage){
        this.filmDbStorage = filmDbStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public boolean addLike(long userId, long filmId) throws WrongIdException {
        if(filmDbStorage.getAllFilms().get(filmId) != null && inMemoryUserStorage.getAllUsers().get(userId) != null) {
            filmDbStorage.getAllFilms().get(filmId).getLikes().add(userId);
        } else throw new WrongIdException("Неверный id");
        return filmDbStorage.getAllFilms().get(filmId).getLikes().contains(userId);
    }

    public boolean deleteLike(long userId, long filmId) throws WrongIdException {
        if(filmDbStorage.getAllFilms().get(filmId) != null && inMemoryUserStorage.getAllUsers().get(userId) != null) {
            filmDbStorage.getAllFilms().get(filmId).getLikes().remove(userId);
        } else throw new WrongIdException("Неверный id");
        return filmDbStorage.getAllFilms().get(filmId).getLikes().contains(userId);
    }

    public long getLikes(long filmId) {
        return filmDbStorage.getAllFilms().get(filmId).getLikes().size() + 1;
    }

    public ArrayList<Film> getBestFilms(int count) {
        ArrayList<Film> bestFilms = new ArrayList<>();
        TreeSet<Film> sortedFilms = new TreeSet<>();
        for(Film film: filmDbStorage.getAllFilms().values()) {
            sortedFilms.add(film);
        }
        for(int i = 0; i < count && i < filmDbStorage.getAllFilms().size(); i++) {
                Film film = sortedFilms.first();
                bestFilms.add(film);
                sortedFilms.remove(film);
        }
        return bestFilms;
    }
}
