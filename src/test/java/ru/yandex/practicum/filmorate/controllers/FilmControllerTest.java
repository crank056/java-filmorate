package ru.yandex.practicum.filmorate.controllers;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {
    private FilmController controller;
    private Film film;

    @BeforeEach
    public void setController() {
        controller = new FilmController();
        film = new Film(0, "TestFilm", "TestFilmDescription",
                LocalDate.of(2020, 12, 30), 150);
    }

    @Test
    void ValidationNameTest() {
        assertTrue(controller.validationFilm(film));
        film.setName("");
        assertFalse(controller.validationFilm(film));
    }

    @Test
    void ValidationDescriptionTest() {
        assertTrue(controller.validationFilm(film));
        film.setDescription("TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription");
        assertFalse(controller.validationFilm(film));
        film.setDescription("TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescriptionTestFilmDescription" +
                "TestFilmDescriptionTestFilmDe");
        assertTrue(controller.validationFilm(film));
    }

    @Test
    void ValidationDateTest() {
        assertTrue(controller.validationFilm(film));
        film.setReleaseDate(LocalDate.of(1850, 12, 30));
        assertFalse(controller.validationFilm(film));
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        assertTrue(controller.validationFilm(film));
    }

    @Test
    void ValidationDurationTest() {
        assertTrue(controller.validationFilm(film));
        film.setDuration(-1);
        assertFalse(controller.validationFilm(film));
        film.setDuration(0);
        assertFalse(controller.validationFilm(film));
        film.setDuration(1);
        assertTrue(controller.validationFilm(film));
    }
}
