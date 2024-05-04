package me.sqsw.vitasofttest.service;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.UserInfoResponse;
import me.sqsw.vitasofttest.exception.RoleNotFoundException;
import me.sqsw.vitasofttest.mapper.UserMapper;
import me.sqsw.vitasofttest.model.Role;
import me.sqsw.vitasofttest.model.User;
import me.sqsw.vitasofttest.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(userMapper::toUserInfo).collect(Collectors.toList());
    }

    @Override
    public UserInfoResponse getUser(Long userId) {
        return userMapper.toUserInfo(userService.getUserById(userId)); //throws exception if not found
    }

    @Override
    public List<UserInfoResponse> searchByUsername(String username) {
        return userService.getUsersByPartialUsername(username).stream().map(userMapper::toUserInfo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInfoResponse grantOperatorPermissions(Long userId) {
        User user = userService.getUserById(userId); //throws exception if not found
        Role role = roleRepository.findByName("ROLE_OPERATOR").orElseThrow(() -> new RoleNotFoundException("ROLE_OPERATOR"));
        user.getRoles().add(role);
        user = userService.save(user);
        return userMapper.toUserInfo(user);
    }

    @Override
    @Transactional
    public UserInfoResponse revokeOperatorPermissions(Long userId) {
        User user = userService.getUserById(userId); //throws exception if not found
        Role role = roleRepository.findByName("ROLE_OPERATOR").orElseThrow(() -> new RoleNotFoundException("ROLE_OPERATOR"));
        user.getRoles().remove(role);
        user = userService.save(user);
        return userMapper.toUserInfo(user);
    }
}