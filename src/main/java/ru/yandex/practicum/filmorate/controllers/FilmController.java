package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
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
    public Film refreshFilm(@RequestBody Film film) throws WrongIdException, ValidationException {
        if(validationFilm(film)) {
            log.info("Запрос PUT films получен");
            Film oldFilm = films.get(film.getId());
            if (oldFilm.getId() != film.getId()) {
                log.error("Выброшено исключение");
                throw new WrongIdException("Id можно изменить только добавив новый фильм!");
            } else films.put(film.getId(), film);
            log.info("Размер фильмохранилища: ", films.size());
            return films.get(film.getId());
        } else throw new ValidationException("Невалидный фильм!");
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    private boolean validationFilm(Film film) {
    boolean isValid = true;
    if(film.getName() == null || film.getName().isBlank()) isValid = false;
    if(film.getDescription().length() > 200) isValid = false;
    LocalDate birthdayOfCinema = LocalDate.of(1895, 12 ,28);
    if(film.getReleaseDate().isAfter(birthdayOfCinema)) isValid = false;
    if(film.getDuration() <= 0) isValid = false;
    return isValid;
    }
}

