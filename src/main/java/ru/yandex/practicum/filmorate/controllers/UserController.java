package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private HashMap<Long, User> users = new HashMap<>();

    @GetMapping
    public ArrayList getAllUsers() {
        ArrayList usersList = new ArrayList();
        for (User user : users.values()) {
            usersList.add(user);
        }
        return usersList;
    }

    @PutMapping
    public User refreshUser(@RequestBody User user) throws ValidationException, WrongIdException {
        log.info("Запрос PUT /users получен");
        if (user.isValid()) {
            if (user.getName() == "") user.setName(user.getLogin());
            log.info(user.getName());
            log.info("Размер хранилища аккаунтов до обновления: {}", users.size());
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.info("Размер хранилища аккаунтов после обновления: {}", users.size());
            } else {
                log.error("Выброшено исключение WrongIdException");
                throw new WrongIdException("Нет пользователя с таким id");
            }
            return users.get(user.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат пользователя!");
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        log.info("Запрос POST /users получен");
        if (user.isValid()) {
            log.info("Размер хранилища аккаунтов до добавления: {}", users.size());
            if (user.getName() == "") user.setName(user.getLogin());
            users.put(user.getId(), user);
            log.info("Размер хранилища аккаунтов после добавления: {}", users.size());
            return users.get(user.getId());
        } else {
            log.error("Выброшено исключение ValidationException");
            throw new ValidationException("Неверный формат юзера!");
        }
    }
}

