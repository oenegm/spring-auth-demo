package com.opay.authorizationservice.auth.dto.keycloak;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRepresentation {

    private String id;
    private String username;
    private Map<String, ?> attributes;

    @Builder.Default
    private boolean enabled = true;
}
