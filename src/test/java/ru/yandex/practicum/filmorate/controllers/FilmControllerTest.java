package ru.yandex.practicum.filmorate.controllers;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {
    private Film film;

    @BeforeEach
    public void setController() {
        film = new Film( "TestFilm", "TestFilmDescription",
                LocalDate.of(2020, 12, 30), 150);
    }

    @Test
    void validateNameTest() {
        assertTrue(film.isValid());
        film.setName("");
        assertFalse(film.isValid());
    }

    @Test
    void validateDescriptionTest() {
        assertTrue(film.isValid());
        film.setDescription("TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription");
        assertFalse(film.isValid());
        film.setDescription("TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDe");
        assertTrue(film.isValid());
    }

    @Test
    void validateDateTest() {
        assertTrue(film.isValid());
        film.setReleaseDate(LocalDate.of(1850, 12, 30));
        assertFalse(film.isValid());
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        assertTrue(film.isValid());
    }

    @Test
    void validateDurationTest() {
        assertTrue(film.isValid());
        film.setDuration(-1);
        assertFalse(film.isValid());
        film.setDuration(0);
        assertFalse(film.isValid());
        film.setDuration(1);
        assertTrue(film.isValid());
    }
}
