package me.sqsw.vitasofttest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.AuthenticationRequest;
import me.sqsw.vitasofttest.dto.AuthenticationResponse;
import me.sqsw.vitasofttest.dto.TokenRequest;
import me.sqsw.vitasofttest.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. Authentication", description = "Contains all the authentication related operations.")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Log in", description = "Log in using username and password")
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest) {
        return authenticationService.login(authRequest);
    }

    @Operation(summary = "Get access token", description = "Get new access token")
    @PostMapping("/token")
    public AuthenticationResponse createAccessToken(@RequestBody TokenRequest authRequest) {
        return authenticationService.createAccessToken(authRequest.getRefreshToken());
    }

    @Operation(summary = "Get refresh token", description = "Get new access and refresh tokens")
    @PostMapping("/refresh")
    public AuthenticationResponse createRefreshToken(@RequestBody TokenRequest authRequest) {
        return authenticationService.createRefreshToken(authRequest.getRefreshToken());
    }
}
