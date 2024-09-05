package com.sparta.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class AuthUser {
    private final Long userId;
    private final String email;

    public AuthUser(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}