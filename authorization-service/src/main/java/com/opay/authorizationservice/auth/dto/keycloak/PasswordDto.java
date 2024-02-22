package com.opay.authorizationservice.auth.dto.keycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class PasswordDto implements Serializable {
    @Builder.Default
    private String type = "password";
    private String value;
    @Builder.Default
    private Boolean temporary = false;
}
