package com.sparta.newsfeed.friend.dto.friendRequest.responseDto;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendRequestResponseDto {
    private Long friendRequestId;
    private Long userId;
    private Long friendUserId;
    private FriendRequest.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public FriendRequestResponseDto(FriendRequest friendRequest, UserEntity user, UserEntity friendUser) {
        this.friendRequestId = friendRequest.getFriendRequestId();
        this.userId = user.getUserId();
        this.friendUserId = friendUser.getUserId();
        this.status = friendRequest.getStatus();
        this.createdAt = friendRequest.getCreatedAt();
        this.modifiedAt = friendRequest.getModifiedAt();
    }
}
