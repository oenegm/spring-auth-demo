package com.opay.resourceservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient client;
    private final UserRepository repository;
    private final UserMapper mapper;


    @Transactional
    public UserDto saveUser(UserDto userDto) {

        var user = mapper.toEntity(userDto);

        user = repository.saveAndFlush(user);

        var systemId = client.saveUser(
                        new UserDto(
                                user.getId(),
                                userDto.username(),
                                userDto.password()
                        )
                )
                .id();

        user.setSystemId(systemId);

        user = repository.saveAndFlush(user);

        return mapper.toDto(user);
    }

    public UserDto getUser(UUID userId) {

        var user = repository.findById(userId).orElseThrow();

        return mapper.toDto(user);
    }

    @Transactional
    public void changePassword(UUID userId, PasswordDto passwordDto) {

        var user = repository.findById(userId).orElseThrow();

        client.changePassword(user.getSystemId(), passwordDto);
    }

    @Transactional
    public void deleteUser(UUID userId) {

        var user = repository.findById(userId).orElseThrow();

        user.setDeleted(true);
        user.setUsername(user.getUsername() + "####" + Instant.now().toString());

        repository.saveAndFlush(user);

        client.deleteUser(user.getSystemId());
    }
}
