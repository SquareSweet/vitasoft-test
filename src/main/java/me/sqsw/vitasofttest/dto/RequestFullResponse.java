package me.sqsw.vitasofttest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.sqsw.vitasofttest.model.RequestState;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class RequestFullResponse {
    private Long id;
    private UserInfoResponse user;
    private String title;
    private String text;
    private RequestState state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentOn;
}
