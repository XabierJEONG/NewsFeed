package com.sparta.newsfeed.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private UserEntity.Gender gender;

    public UserRegisterRequestDto(  // 어노테이션 추가
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("gender") UserEntity.Gender gender
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
}
