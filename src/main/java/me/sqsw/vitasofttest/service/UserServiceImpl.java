package me.sqsw.vitasofttest.service;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;
import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.exception.UserNotFoundException;
import me.sqsw.vitasofttest.model.User;
import me.sqsw.vitasofttest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User id '%s' not found", userId)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User '%s' not found", username)));
    }

    @Override
    public List<User> getUsersByPartialUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public List<User> getAllUsers() {
        return IterableUtils.toList(userRepository.findAll());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
