package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UserControlller {
    private HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public HashMap getAllUsers() {
        return users;
    }

    @PutMapping
    public User refreshUser(@RequestBody User user) throws WrongIdException {
        User oldUser = users.get(user.getId());
        if (oldUser.getId() != user.getId()) {
            throw new WrongIdException("Id можно изменить только создав нового пользователя!");
        } else users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    private boolean validationUser(User user) {
        boolean isValid = true;
        if(user.getEmail().isBlank() || !user.getEmail().contains("@")) isValid = false;
        if(user.getLogin().isBlank() || user.getLogin().contains(" ")) isValid = false;
        if(user.getBirthday().isBefore(LocalDate.now())) isValid = false;
        return isValid;
    }
}

