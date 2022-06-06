package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {

    User userAdd(User user);

    boolean userDelete(User user);

    User userRefresh(User user);

    ArrayList getAllUsers();


}
