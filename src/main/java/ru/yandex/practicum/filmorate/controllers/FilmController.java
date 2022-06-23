package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    InMemoryFilmStorage inMemoryFilmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public HashMap<Long, Film> getAllFilms() {
    return inMemoryFilmStorage.getAllFilms();
    }

    @PutMapping
    public Film refreshFilm(@RequestBody Film film) {
        log.info("Запрос PUT /films получен");
        return inMemoryFilmStorage.filmRefresh(film);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        log.info("Запрос POST /films получен");
        return inMemoryFilmStorage.filmAdd(film);
    }

    @DeleteMapping
    public boolean deleteFilm(@RequestBody Film film) {
        log.info("Запрос DELETE /films получен");
        return inMemoryFilmStorage.filmDelete(film);
    }
}

