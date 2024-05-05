package me.sqsw.vitasofttest.service;

import me.sqsw.vitasofttest.dto.UserInfoResponse;

import java.util.List;

public interface AdminService {
    List<UserInfoResponse> getAllUsers();

    UserInfoResponse getUser(Long userId);

    List<UserInfoResponse> searchByUsername(String username);

    UserInfoResponse grantOperatorPermissions(Long userId);

    UserInfoResponse revokeOperatorPermissions(Long userId);
}