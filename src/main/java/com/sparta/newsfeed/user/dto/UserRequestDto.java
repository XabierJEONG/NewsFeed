package com.sparta.newsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userName;
    private String email;
    private String currentPassword;
    private String newPassword;

    public UserRequestDto() {
    }

    public UserRequestDto(String userName, String email, String currentPassword, String newPassword) {
        this.userName = userName;
        this.email = email;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}

