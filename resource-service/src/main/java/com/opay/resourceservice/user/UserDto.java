package com.opay.resourceservice.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

/**
 * DTO for {@link User}
 */
public record UserDto(

        UUID id,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String username,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[_\\W])(?!.*\\s).{8,}$")
        String password
) {
}
