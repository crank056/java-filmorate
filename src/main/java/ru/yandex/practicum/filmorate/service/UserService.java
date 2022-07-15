package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public boolean addFriend(long userId, long friendId) throws WrongIdException {
        if (userStorage.getAllUsers().get(userId) != null && userStorage.getAllUsers().get(friendId) != null) {
            userStorage.getAllUsers().get(userId).getFriends().add(friendId);
            userStorage.getAllUsers().get(friendId).getFriends().add(userId);
        } else throw new WrongIdException("Неверный id пользователя или друга");
        return userStorage.getAllUsers().get(userId).getFriends().contains(friendId);
    }

    public boolean deleteFriend(long userId, long friendId) throws WrongIdException {
        if (userStorage.getAllUsers().get(userId).getFriends().contains(friendId)) {
            userStorage.getAllUsers().get(userId).getFriends().remove(friendId);
            userStorage.getAllUsers().get(friendId).getFriends().remove(userId);
        } else throw new WrongIdException("Нет такого друга или неверный id");
        return !userStorage.getAllUsers().get(userId).getFriends().contains(friendId);
    }

    public ArrayList<User> getFriendsList(long userId) throws WrongIdException {
        ArrayList<User> friends = new ArrayList<>();
        if (userStorage.getAllUsers().get(userId) != null) {
            for (Long frienId : userStorage.getAllUsers().get(userId).getFriends()) {
                friends.add(userStorage.getAllUsers().get(frienId));
            }
        } else throw new WrongIdException("Неверный id");
        return friends;
    }

    public ArrayList<User> showCommonFriends(long userId, long userForComparison) throws WrongIdException {
        ArrayList<User> commonFriends = new ArrayList<>();
        if (userStorage.getAllUsers().get(userId) != null && userStorage.getAllUsers().get(userForComparison) != null) {
            Set<Long> userFriend = userStorage.getAllUsers().get(userId).getFriends();
            Set<Long> userForComparisonFriend = userStorage.getAllUsers().get(userForComparison).getFriends();
            for (Long id : userFriend) {
                if (userForComparisonFriend.contains(id)) {
                    commonFriends.add(userStorage.getAllUsers().get(id));
                }
            }
        } else throw new WrongIdException("Неверный id");
        return commonFriends;
    }
}