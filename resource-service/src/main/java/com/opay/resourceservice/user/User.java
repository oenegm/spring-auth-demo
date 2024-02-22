package com.opay.resourceservice.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "users_username_key", columnList = "username", unique = true),
})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "system_id")
    private UUID systemId;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @PrePersist
    void prePersist() {
        this.deleted = false;
    }
}
