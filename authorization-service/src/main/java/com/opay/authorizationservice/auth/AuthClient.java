package com.opay.authorizationservice.auth;

import com.opay.authorizationservice.auth.dto.keycloak.TokenResponse;
import com.opay.authorizationservice.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthClient {

    public static final String CLIENT_ID = "client_id";
    public static final String GRANT_TYPE = "grant_type";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String SCOPE = "scope";
    public static final String REFRESH_TOKEN = "refresh_token";

    private final KeycloakProperties properties;
    private final RestClient http;


    public TokenResponse authenticate(String username, String password) {

        var body = new LinkedMultiValueMap<String, String>();
        body.add(CLIENT_ID, properties.getAuth().getClientId());
        body.add(GRANT_TYPE, PASSWORD);
        body.add(SCOPE, properties.getAuth().getScope());
        body.add(USERNAME, username.toLowerCase());
        body.add(PASSWORD, password);

        return getTokens(body);
    }

    public TokenResponse refreshToken(String refreshToken) {

        var body = new LinkedMultiValueMap<String, String>();
        body.add(CLIENT_ID, properties.getAuth().getClientId());
        body.add(GRANT_TYPE, REFRESH_TOKEN);
        body.add(REFRESH_TOKEN, refreshToken);

        return getTokens(body);
    }

    private TokenResponse getTokens(LinkedMultiValueMap<String, String> body) {
        return http.post()
                .uri(String.format("%s/realms/%s/protocol/openid-connect/token", properties.getUrl(), properties.getRealm()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            log.error("Client Error Status " + response.getStatusCode());
                            log.error("Client Error Body " + new String(response.getBody().readAllBytes()));
                            throw new RuntimeException("bad.credentials");
                        }
                )
                .body(TokenResponse.class);
    }
}