package com.sparta.newsfeed.friend.dto.friendRequest;

import com.sparta.newsfeed.friend.entity.FriendRequest;
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

    public FriendRequestResponseDto(FriendRequest friendRequest) {
        this.friendRequestId = friendRequest.getFriendRequestId();
        this.userId = friendRequest.getUserId();
        this.friendUserId = friendRequest.getFriendUserId();
        this.status = friendRequest.getStatus();
        this.createdAt = friendRequest.getCreatedAt();
        this.modifiedAt = friendRequest.getModifiedAt();
    }
}
