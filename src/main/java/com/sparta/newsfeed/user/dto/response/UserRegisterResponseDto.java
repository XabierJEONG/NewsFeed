package com.sparta.newsfeed.user.dto.response;

import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRegisterResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final UserEntity.Gender gender;
    private final LocalDateTime createdAt;

    public UserRegisterResponseDto(Long id, String username, String email, UserEntity.Gender gender, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.createdAt = createdAt;
    }
}
