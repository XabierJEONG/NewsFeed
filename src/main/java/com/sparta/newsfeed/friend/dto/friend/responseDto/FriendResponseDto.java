package com.sparta.newsfeed.friend.dto.friend.responseDto;

import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class FriendResponseDto {
    private Long id;
    private Long userId;
    private Long friendUserId;

    public FriendResponseDto(Friend friend, UserEntity user, UserEntity friendUser) {
        this.id = friend.getId();
        this.userId = user.getUserId();
        this.friendUserId = friendUser.getUserId();
    }
}
