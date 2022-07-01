package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.Map;

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
    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> films = new ArrayList<>();
        films.addAll(inMemoryFilmStorage.getAllFilms().values());
        return films;
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

    @GetMapping("/{id}")
    public Film getFilmFromId(@PathVariable long id) throws WrongIdException {
        return inMemoryFilmStorage.getFilmFromId(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public boolean addLike(@PathVariable long id, @PathVariable long userId) throws WrongIdException {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public boolean deleteLike(@PathVariable long id, @PathVariable long userId) throws WrongIdException {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getBestFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getBestFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("Объект не прошел валидацию", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleWrongIdException(final WrongIdException e) {
        return Map.of("объект с таким Id не найден", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeException(final RuntimeException e) {
        return Map.of("Возникло исключение", e.getMessage());
    }
}

