package com.opay.authorizationservice.auth;

import com.opay.authorizationservice.auth.dto.AccessTokenRequest;
import com.opay.authorizationservice.auth.dto.RefreshTokenRequest;
import com.opay.authorizationservice.auth.dto.keycloak.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient client;


    public TokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        return client.authenticate(accessTokenRequest.username(), accessTokenRequest.password());
    }

    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return client.refreshToken(refreshTokenRequest.refreshToken());
    }


}
