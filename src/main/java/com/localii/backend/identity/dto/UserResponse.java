package com.localii.backend.identity.dto;

import com.localii.backend.identity.entity.User;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserResponse {

    private final UUID id;
    private final String email;
    private final String phone;
    private final User.Role role;
    private final User.Status status;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.status = user.getStatus();
    }
}