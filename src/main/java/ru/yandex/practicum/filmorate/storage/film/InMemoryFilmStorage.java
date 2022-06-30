package ru.yandex.practicum.filmorate.storage.film;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private HashMap<Long, Film> films = new HashMap<>();
    private static long lastUsedId = 1;

    @SneakyThrows
    @Override
    public Film filmRefresh(Film film) {
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

    @Override
    public HashMap<Long, Film> getAllFilms() {
        return films;
    }

    @Override
    public boolean filmDelete(Film film) {
        boolean isDone = true;
        if(films.containsValue(film)) films.remove(film.getId());
        else isDone = false;
        return isDone;
    }

    @SneakyThrows
    @Override
    public Film filmAdd(Film film) {
        if (film.isValid()) {
            film.setId(getNextId());
            films.put(film.getId(), film);
            log.info("Размер фильмохранилища после добавления: {}", films.size());
            return films.get(film.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат фильма");
        }
    }

    @Override
    public Film getFilmFromId(long id) throws WrongIdException {
        if(films.containsKey(id)) {
        return films.get(id);}
        else throw new WrongIdException("Неверный id");
    }

    private long getNextId() {
            return lastUsedId++;
    }
}
