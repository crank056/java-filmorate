package ru.yandex.practicum.filmorate.controllers;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {
    private User user;

    @BeforeEach
    void setController() {
        user = new User( "email@mail.ru", "testLogin", "TestName",
                LocalDate.of(2000, 12, 31));
    }

    @Test
    void emailValidationTest() {
        assertTrue(user.isValid());
        user.setEmail("");
        assertFalse(user.isValid());
        user.setEmail("@");
        assertTrue(user.isValid());
        user.setEmail("test");
        assertFalse(user.isValid());
    }

    @Test
    void loginValidationTest() {
        assertTrue(user.isValid());
        user.setLogin("");
        assertFalse(user.isValid());
        user.setLogin("test");
        assertTrue(user.isValid());
        user.setLogin("te st");
        assertFalse(user.isValid());
    }

    @Test
    void nameValidationTest() {
        assertTrue(user.isValid());
        user.setName("");
        assertTrue(user.isValid());
    }

    @Test
    void birtdayValidationTest() {
        assertTrue(user.isValid());
        user.setBirthday(LocalDate.now().plusDays(1));
        assertFalse(user.isValid());
        user.setBirthday(LocalDate.now());
        assertTrue(user.isValid());
        user.setBirthday(LocalDate.of(2050, 12, 31));
        assertFalse(user.isValid());
    }
}
