package com.sparta.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class WithdrawRequestDto {

    private String email;
    private String password;

    public WithdrawRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
