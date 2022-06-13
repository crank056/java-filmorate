package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private HashMap<Long, Film> films = new HashMap<>();

    @GetMapping
    public ArrayList getAllFilms() {
        ArrayList<Film> filmsList = new ArrayList<>();
        for (Film film : films.values()) {
            filmsList.add(film);
        }
        return filmsList;
    }

    @PutMapping
    public Film refreshFilm(@RequestBody Film film) throws ValidationException, WrongIdException {
        log.info("Запрос PUT /films получен");
        if (film.isValid()) {
            if (films.containsKey(film.getId())) {
                log.info("Размер фильмохранилища до обновления фильма: {}", films.size());
                films.put(film.getId(), film);
                log.info("Размер фильмохранилища после обновления фильма: {}", films.size());
            } else {
                log.error("Выброшено исключение WrongIdException");
                throw new WrongIdException("Нет фильма с таким id");
            }
            return films.get(film.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат фильма!");
        }
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        log.info("Запрос POST /films получен");
        if (film.isValid()) {
            films.put(film.getId(), film);
            log.info("Размер фильмохранилища после добавления: {}", films.size());
            return films.get(film.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат фильма");
        }
    }
}

