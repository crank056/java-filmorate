package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public boolean addFriend(long userId, long friendId) {
        userStorage.getAllUsers().get(userId).getFriends().add(friendId);
        return userStorage.getAllUsers().get(userId).getFriends().contains(friendId);
    }

    public boolean deleteFriend(long userId, long friendId) {
        userStorage.getAllUsers().get(userId).getFriends().remove(friendId);
        return !userStorage.getAllUsers().get(userId).getFriends().contains(friendId);
    }

    public ArrayList<User> getFriendsList(long userId) {
        ArrayList<User> friends = null;
        for(Long frienId: userStorage.getAllUsers().get(userId).getFriends()){
            friends.add(userStorage.getAllUsers().get(frienId));
        }
        return friends;
    }

    public ArrayList<User> showCommonFriends(long userId, long userForComparison) {
        Set<Long> userFriend = userStorage.getAllUsers().get(userId).getFriends();
        Set<Long> userForComparisonFriend = userStorage.getAllUsers().get(userForComparison).getFriends();
        ArrayList<User> commonFriends = null;
        for(Long id: userFriend) {
            if(userForComparisonFriend.contains(id)) {
                commonFriends.add(userStorage.getAllUsers().get(id));
            }
        }
        return commonFriends;
    }
}
