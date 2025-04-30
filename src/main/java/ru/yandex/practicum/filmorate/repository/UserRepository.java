package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();
    User getById(int id);
    User create(User user);
    User update(User user);
    void addFriend(int userId, int friendId);
    void removeFriend(int userId, int friendId);
    Collection<User> getFriends(int userId);
    Collection<User> getCommonFriends(int userId, int otherId);
}