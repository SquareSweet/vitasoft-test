package me.sqsw.vitasofttest.mapper;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.RequestCreateRequest;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.model.Request;
import me.sqsw.vitasofttest.model.RequestState;
import me.sqsw.vitasofttest.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final UserMapper userMapper;

    public Request toRequest(RequestCreateRequest request, User user) {
        return Request.builder()
                .user(user)
                .title(request.getTitle())
                .text(request.getText())
                .state(RequestState.DRAFT)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public RequestFullResponse toRequestFull(Request request) {
        return RequestFullResponse.builder()
                .id(request.getId())
                .user(userMapper.toUserInfo(request.getUser()))
                .title(request.getTitle())
                .text(request.getText())
                .state(request.getState())
                .createdOn(request.getCreatedOn())
                .sentOn(request.getSentOn())
                .build();
    }

    public RequestShortResponse requestShort(Request request) {
        return RequestShortResponse.builder()
                .id(request.getId())
                .username(request.getUser().getUsername())
                .title(request.getTitle())
                .state(request.getState())
                .createdOn(request.getCreatedOn())
                .sentOn(request.getSentOn())
                .build();
    }
}
