package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void addFriend(int userId, int friendId) {
        userRepository.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        userRepository.removeFriend(userId, friendId);
    }

    public Collection<User> getFriends(int userId) {
        return userRepository.getFriends(userId);
    }

    public Collection<User> getCommonFriends(int userId, int otherId) {
        return userRepository.getCommonFriends(userId, otherId);
    }
}