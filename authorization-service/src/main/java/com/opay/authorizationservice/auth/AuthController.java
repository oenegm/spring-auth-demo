package com.opay.authorizationservice.auth;

import com.opay.authorizationservice.auth.dto.AccessTokenRequest;
import com.opay.authorizationservice.auth.dto.RefreshTokenRequest;
import com.opay.authorizationservice.auth.dto.keycloak.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;


    @PostMapping
    public ResponseEntity<TokenResponse> authenticate(
            @Valid @RequestBody AccessTokenRequest accessTokenRequest
    ) {

        var tokenResponse = service.authenticate(accessTokenRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {

        var tokenResponse = service.refreshToken(refreshTokenRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenResponse);
    }


}
