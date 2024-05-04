package me.sqsw.vitasofttest.service;

import me.sqsw.vitasofttest.dto.AuthenticationRequest;
import me.sqsw.vitasofttest.dto.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest authRequest);
    AuthenticationResponse createAccessToken(String refreshToken);
    AuthenticationResponse createRefreshToken(String refreshToken);
}
