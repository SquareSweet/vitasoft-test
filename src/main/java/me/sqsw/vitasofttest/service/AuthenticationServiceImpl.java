package me.sqsw.vitasofttest.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.AuthenticationRequest;
import me.sqsw.vitasofttest.dto.AuthenticationResponse;
import me.sqsw.vitasofttest.exception.InvalidTokenException;
import me.sqsw.vitasofttest.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDetailsService userDetailsService;
    private final JwtUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String accessToken = tokenUtils.generateAccessToken(userDetails);
        String refreshToken = tokenUtils.generateRefreshToken(userDetails);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public AuthenticationResponse createAccessToken(String refreshToken) {
        if (tokenUtils.validateRefreshToken(refreshToken)) {
            Claims claims = tokenUtils.getRefreshClaims(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            String accessToken = tokenUtils.generateAccessToken(userDetails);
            return new AuthenticationResponse(accessToken, null);
        }
        throw new InvalidTokenException("Invalid refresh token");
    }

    @Override
    public AuthenticationResponse createRefreshToken(String refreshToken) {
        if (tokenUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = tokenUtils.getRefreshClaims(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            String accessToken = tokenUtils.generateAccessToken(userDetails);
            String newRefreshToken = tokenUtils.generateRefreshToken(userDetails);
            return new AuthenticationResponse(accessToken, newRefreshToken);
        }
        throw new InvalidTokenException("Invalid refresh token");
    }
}
