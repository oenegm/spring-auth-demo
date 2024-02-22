package com.opay.resourceservice.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(value = "authorization-service", url = "${service.auth.url}/users")
public interface UserClient {

    @PostMapping
    UserDto saveUser(@RequestBody UserDto userDto);

    @PutMapping("{systemId}/password")
    void changePassword(
            @PathVariable("systemId") UUID systemId,
            @RequestBody PasswordDto passwordDto
    );

    @DeleteMapping("{systemId}")
    void deleteUser(@PathVariable("systemId") UUID systemId);
}
