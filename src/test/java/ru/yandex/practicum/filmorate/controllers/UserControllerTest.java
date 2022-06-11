package ru.yandex.practicum.filmorate.controllers;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;

public class UserControllerTest {
    private UserController controller;
    private User user;

    @BeforeEach
    void setController() {
        controller = new UserController();
        user = new User(0, "email@mail.ru", "testLogin", "TestName",
                LocalDate.of(2000, 12, 31));
    }

    @Test
    void emailValidationTest() {
        assertTrue(controller.validationUser(user));
        user.setEmail("");
        assertFalse(controller.validationUser(user));
        user.setEmail("@");
        assertTrue(controller.validationUser(user));
        user.setEmail("test");
        assertFalse(controller.validationUser(user));
    }

    @Test
    void loginValidationTest() {
        assertTrue(controller.validationUser(user));
        user.setLogin("");
        assertFalse(controller.validationUser(user));
        user.setLogin("test");
        assertTrue(controller.validationUser(user));
        user.setLogin("te st");
        assertFalse(controller.validationUser(user));
    }

    @Test
    void nameValidationTest() {
        assertTrue(controller.validationUser(user));
        user.setName("");
        assertTrue(controller.validationUser(user));
    }

    @Test
    void birtdayValidationTest() {
        assertTrue(controller.validationUser(user));
        user.setBirthday(LocalDate.now().plusDays(1));
        assertFalse(controller.validationUser(user));
        user.setBirthday(LocalDate.now());
        assertTrue(controller.validationUser(user));
        user.setBirthday(LocalDate.of(2050, 12, 31));
        assertFalse(controller.validationUser(user));
    }
}
