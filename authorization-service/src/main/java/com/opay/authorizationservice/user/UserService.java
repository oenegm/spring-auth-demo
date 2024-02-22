package com.opay.authorizationservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient client;


    public UserDto saveUser(UserDto userDto) {

        var userId = client.saveUser(
                userDto.id(),
                userDto.username(),
                userDto.password()
        );

        return new UserDto(userId, null, null);
    }

    public void changePassword(UUID systemId, PasswordDto passwordDto) {
        client.changePassword(systemId, passwordDto.password());
    }

    public void deleteUser(UUID systemId) {
        client.deleteUser(systemId);
    }
}
