package ru.yandex.practicum.filmorate.controllers;

import com.sun.jdi.LongValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    InMemoryUserStorage inMemoryUserStorage;
    UserService userService;

    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @GetMapping
    public HashMap<Long, User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @PutMapping
    public User refreshUser(@RequestBody User user) throws ValidationException, WrongIdException {
        log.info("Запрос PUT /users получен");
        return inMemoryUserStorage.userRefresh(user);
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        log.info("Запрос POST /users получен");
        return inMemoryUserStorage.userAdd(user);
    }
}

