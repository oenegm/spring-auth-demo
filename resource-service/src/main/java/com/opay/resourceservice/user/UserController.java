package com.opay.resourceservice.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @GetMapping
    public ResponseEntity<UserDto> getUser(
            @RequestHeader("User-Id") UUID userId
    ) {

        var user = service.getUser(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {

        var savedUser = service.saveUser(userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PatchMapping("/password")
    public ResponseEntity<UserDto> changePassword(
            @RequestHeader("User-Id") UUID userId,
            @Valid @RequestBody PasswordDto passwordDto
    ) {

        service.changePassword(userId, passwordDto);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @RequestHeader("User-Id") UUID userId
    ) {

        service.deleteUser(userId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
