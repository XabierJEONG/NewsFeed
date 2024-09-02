package com.sparta.newsfeed.user.dto.request;

import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private UserEntity.Gender gender;
}
