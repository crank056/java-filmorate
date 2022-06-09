package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public HashMap homePage() {
        return films;
    }

    @PutMapping
    public Film refreshFilm(@Valid @RequestBody Film film) throws WrongIdException {
        log.info("Запрос PUT films получен");
        Film oldFilm = films.get(film.getId());
        if (oldFilm.getId() != film.getId()) {
            log.error("Выброшено исключение");
            throw new WrongIdException("Id можно изменить только добавив новый фильм!");
        } else films.put(film.getId(), film);
        log.info("Размер фильмохранилища: ",films.size());
        return films.get(film.getId());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {

        films.put(film.getId(), film);
        return films.get(film.getId());
    }
}
