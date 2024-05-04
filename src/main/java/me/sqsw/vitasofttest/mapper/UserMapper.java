package me.sqsw.vitasofttest.mapper;

import me.sqsw.vitasofttest.dto.UserInfoResponse;
import me.sqsw.vitasofttest.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfoResponse toUserInfo(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}