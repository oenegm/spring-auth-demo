package com.opay.authorizationservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("keycloak")
public class KeycloakProperties {

    private String url;
    private String realm;
    private Admin admin;
    private Auth auth;


    @Data
    public static class Admin {
        private String clientId;
        private String username;
        private String password;
    }

    @Data
    public static class Auth {
        private String clientId;
        private String scope;
    }
}
