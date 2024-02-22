package com.opay.authorizationservice.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UserDto(

        UUID id,

        String username,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[_\\W])(?!.*\\s).{8,}$")
        String password
) {
}
