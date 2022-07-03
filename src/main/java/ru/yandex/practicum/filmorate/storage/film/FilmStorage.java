package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

public interface FilmStorage {

    Film filmAdd(Film film);

    boolean filmDelete(Film film);

    Film filmRefresh(Film film);

    HashMap<Long, Film> getAllFilms();

    Film getFilmFromId(long id) throws WrongIdException;

}
