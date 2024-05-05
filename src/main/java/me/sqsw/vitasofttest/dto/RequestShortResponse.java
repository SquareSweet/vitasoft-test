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
public class RequestShortResponse {
    private Long id;
    private String username;
    private String title;
    private RequestState state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentOn;
}
