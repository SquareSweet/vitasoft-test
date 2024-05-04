package me.sqsw.vitasofttest.controller;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.AuthenticationRequest;
import me.sqsw.vitasofttest.dto.AuthenticationResponse;
import me.sqsw.vitasofttest.dto.TokenRequest;
import me.sqsw.vitasofttest.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest) {
        return authenticationService.login(authRequest);
    }

    @PostMapping("/token")
    public AuthenticationResponse createAccessToken(@RequestBody TokenRequest authRequest) {
        return authenticationService.createAccessToken(authRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    public AuthenticationResponse createRefreshToken(@RequestBody TokenRequest authRequest) {
        return authenticationService.createRefreshToken(authRequest.getRefreshToken());
    }
}
