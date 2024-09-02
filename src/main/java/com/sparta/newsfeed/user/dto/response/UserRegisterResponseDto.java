package com.sparta.newsfeed.user.dto.response;

import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRegisterResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final UserEntity.Gender gender;
    private final LocalDateTime createAt;

    public UserRegisterResponseDto(Long id, String username, String email, String password, UserEntity.Gender gender, LocalDateTime createAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.createAt = createAt;
    }
}
