package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Map;

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
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(inMemoryUserStorage.getAllUsers().values());
        return users;
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

    @GetMapping("/{id}")
    public User getUserFromId(@PathVariable long id) throws WrongIdException {
        log.info("Запрос GET /users/{id} получен");
        return inMemoryUserStorage.getUserFromId(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public boolean addFriend(@PathVariable long id, @PathVariable long friendId) throws WrongIdException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public boolean deleteFriend(@PathVariable long id, @PathVariable long friendId) throws WrongIdException {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public ArrayList<User> getFriends(@PathVariable long id) throws WrongIdException {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) throws WrongIdException {
        return userService.showCommonFriends(id, otherId);
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


