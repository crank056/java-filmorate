package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

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
        log.info("Запрос PUT /films получен");
        if(validationFilm(film)) {
            Film oldFilm = films.get(film.getId());
            if (oldFilm.getId() != film.getId()) {
                log.error("Выброшено исключение WrongIdException");
                throw new WrongIdException("Id можно изменить только добавив новый фильм!");
            } else films.put(film.getId(), film);
            log.info("Размер фильмохранилища после обновления фильма: ", films.size());
            return films.get(film.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат фильма!");
        }
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        log.info("Запрос POST /films получен");
        if (validationFilm(film)) {
            films.put(film.getId(), film);
            log.info("Размер фильмохранилища после добавления: ", films.size());
            return films.get(film.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат фильма");
        }
    }


    private boolean validationFilm(Film film) {
    boolean isValid = true;
    if(film.getName() == null || film.getName().isBlank()) {
        isValid = false;
        log.info("Имя не существует или пустое");
    }
    if(film.getDescription().length() > 200) {
        isValid = false;
        log.info("Размер описания превышает 200 символов");
    }
    LocalDate birthdayOfCinema = LocalDate.of(1895, 12 ,28);
    if(film.getReleaseDate().isBefore(birthdayOfCinema)) {
        isValid = false;
        log.info("Дата релиза раньше даты рождения первого фильма в истории!");
    }
    if(film.getDuration() <= 0) {
        isValid = false;
        log.info("Продолжительность равна или меньше нуля");
    }
    return isValid;
    }
}

