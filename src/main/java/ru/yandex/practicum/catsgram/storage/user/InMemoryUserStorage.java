package ru.yandex.practicum.catsgram.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Set<Integer>> friends = new HashMap<>();
    private int nextId = 1;

    @Override
    public User create(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        friends.put(user.getId(), new HashSet<>());
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(int id) {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void addFriend(int userId, int friendId) {
        friends.get(userId).add(friendId);
        friends.get(friendId).add(userId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        friends.get(userId).remove(friendId);
        friends.get(friendId).remove(userId);
    }

    @Override
    public Collection<User> getFriends(int userId) {
        Set<User> userFriends = new HashSet<>();
        for (Integer friendId : friends.get(userId)) {
            userFriends.add(users.get(friendId));
        }
        return userFriends;
    }

    @Override
    public Collection<User> getCommonFriends(int userId, int otherId) {
        Set<User> commonFriends = new HashSet<>();
        for (Integer friendId : friends.get(userId)) {
            if (friends.get(otherId).contains(friendId)) {
                commonFriends.add(users.get(friendId));
            }
        }
        return commonFriends;
    }
}