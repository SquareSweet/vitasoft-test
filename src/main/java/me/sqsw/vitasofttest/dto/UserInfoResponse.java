package me.sqsw.vitasofttest.dto;

import lombok.*;
import me.sqsw.vitasofttest.model.Role;

import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class UserInfoResponse {
    private Long id;
    private String username;
    private Set<Role> roles;
}
