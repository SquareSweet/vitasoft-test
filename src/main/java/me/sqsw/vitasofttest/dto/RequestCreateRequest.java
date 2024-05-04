package me.sqsw.vitasofttest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RequestCreateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
}
