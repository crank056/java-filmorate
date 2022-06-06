package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

    Film filmAdd(Film film);

    boolean filmDelete(Film film);

    Film filmRefresh(Film film);

    ArrayList getAllFilms();

}
