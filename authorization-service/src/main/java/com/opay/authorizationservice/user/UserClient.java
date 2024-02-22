package com.opay.authorizationservice.user;

import com.opay.authorizationservice.auth.dto.keycloak.PasswordDto;
import com.opay.authorizationservice.auth.dto.keycloak.TokenResponse;
import com.opay.authorizationservice.auth.dto.keycloak.UserRepresentation;
import com.opay.authorizationservice.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserClient {

    public static final String CLIENT_ID = "client_id";
    public static final String GRANT_TYPE = "grant_type";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";

    private final KeycloakProperties properties;
    private final RestClient http;


    public UUID saveUser(UUID id, String handler, String password) {

        var userRepresentation = UserRepresentation.builder()
                .id(id.toString())
                .username(handler.toLowerCase())
                .attributes(Map.of("id", id.toString()))
                .build();

        http.post()
                .uri(String.format("%s/admin/realms/%s/users", properties.getUrl(), properties.getRealm()))
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBearerAuth(getMasterToken());
                })
                .body(userRepresentation)
                .retrieve()
                .toBodilessEntity();

        List<UserRepresentation> userRepresentationList = http.get()
                .uri(String.format("%s/admin/realms/%s/users?username=%s&exact=true", properties.getUrl(), properties.getRealm(), handler))
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBearerAuth(getMasterToken());
                })
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert userRepresentationList != null;

        changePassword(UUID.fromString(userRepresentationList.get(0).getId()), password);

        return UUID.fromString(userRepresentationList.get(0).getId());
    }

    public void changePassword(UUID systemId, String newPassword) {

        var passwordDto = PasswordDto.builder()
                .value(newPassword)
                .build();

        http.put()
                .uri(String.format("%s/admin/realms/%s/users/%s/reset-password", properties.getUrl(), properties.getRealm(), systemId))
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBearerAuth(getMasterToken());
                })
                .body(passwordDto)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteUser(UUID systemId) {
        http.delete()
                .uri(String.format("%s/admin/realms/%s/users/%s", properties.getUrl(), properties.getRealm(), systemId))
                .headers(headers -> headers.setBearerAuth(getMasterToken()))
                .retrieve()
                .toBodilessEntity();
    }

    private String getMasterToken() {

        var body = new LinkedMultiValueMap<String, String>();
        body.add(CLIENT_ID, properties.getAdmin().getClientId());
        body.add(GRANT_TYPE, PASSWORD);
        body.add(USERNAME, properties.getAdmin().getUsername());
        body.add(PASSWORD, properties.getAdmin().getPassword());

        TokenResponse tokenResponse = http.post()
                .uri(String.format("%s/realms/master/protocol/openid-connect/token", properties.getUrl()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(TokenResponse.class);

        assert tokenResponse != null;

        return tokenResponse.getAccessToken();
    }
}
