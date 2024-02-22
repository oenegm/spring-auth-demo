package com.opay.authorizationservice.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PostMapping
    public ResponseEntity<UserDto> saveUser(
            @Valid @RequestBody UserDto userDto
    ) {

        var savedUser = service.saveUser(userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PutMapping("{systemId}/password")
    public ResponseEntity<UserDto> changePassword(
            @PathVariable("systemId") UUID systemId,
            @Valid @RequestBody PasswordDto passwordDto
    ) {

        service.changePassword(systemId, passwordDto);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("{systemId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("systemId") UUID systemId
    ) {

        service.deleteUser(systemId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
