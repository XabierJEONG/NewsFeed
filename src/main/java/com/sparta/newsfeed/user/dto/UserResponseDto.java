package com.sparta.newsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private String gender;
    private String introduce;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserResponseDto() {

    }

    public UserResponseDto(Long userId, String userName, String email, String gender, String introduce, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.introduce = introduce;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
