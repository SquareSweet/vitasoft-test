package me.sqsw.vitasofttest.service;

import me.sqsw.vitasofttest.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);

    User getUserByUsername(String username);

    List<User> getUsersByPartialUsername(String username);

    List<User> getAllUsers();

    User save(User user);
}
