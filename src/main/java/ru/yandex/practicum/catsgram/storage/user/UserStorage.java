package ru.yandex.practicum.catsgram.storage.user;


import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user);

    User update(User user);

    User getUserById(int id);

    Collection<User> findAll();

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Collection<User> getFriends(int userId);

    Collection<User> getCommonFriends(int userId, int otherId);
}