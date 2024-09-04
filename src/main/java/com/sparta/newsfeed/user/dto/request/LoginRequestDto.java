package com.sparta.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

