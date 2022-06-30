package ru.yandex.practicum.filmorate.storage.user;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private HashMap<Long, User> users = new HashMap<>();
    private static long lastUsedId = 1;

    @SneakyThrows
    @Override
    public User userAdd(User user) {
        if (user.isValid()) {
            user.setId(getNextId());
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

    @Override
    public boolean userDelete(User user) {
        boolean isDone = true;
        if(users.containsValue(user)) users.remove(user.getId());
        else isDone = false;
        return isDone;
    }

    @SneakyThrows
    @Override
    public User userRefresh(User user) {
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

    @Override
    public HashMap<Long, User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserFromId(long id) throws WrongIdException {
        if(id < 0) throw new WrongIdException("Id меньше 0");
        return users.get(id);
    }

    private long getNextId() {
        return lastUsedId++;
    }
}
