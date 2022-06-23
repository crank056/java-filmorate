package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserStorage {

    User userAdd(User user);

    boolean userDelete(User user);

    User userRefresh(User user);

    HashMap<Long, User> getAllUsers();


}
