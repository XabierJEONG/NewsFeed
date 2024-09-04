package com.sparta.newsfeed.friend.dto.friend;

import java.time.LocalDateTime;

public class FriendResponseDto {
    private Long id;
    private Long userId;
    private Long friendUserId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
