package com.sparta.newsfeed.user.dto.request;

import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private UserEntity.Gender gender;

    public UserRegisterRequestDto(
            String username,
            String email,
            String password,
            UserEntity.Gender gender
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
}
