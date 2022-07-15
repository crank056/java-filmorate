package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

public interface UserStorage {

    User userAdd(User user);

    boolean userDelete(User user);

    boolean userRefresh(User user);

    HashMap<Long, User> getAllUsers();

    User getUserFromId(long id) throws WrongIdException;


}
