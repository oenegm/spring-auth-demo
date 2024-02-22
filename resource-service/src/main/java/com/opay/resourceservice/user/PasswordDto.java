package com.opay.resourceservice.user;

import jakarta.validation.constraints.Pattern;

public record PasswordDto(
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[_\\W])(?!.*\\s).{8,}$")
        String password
) {
}
