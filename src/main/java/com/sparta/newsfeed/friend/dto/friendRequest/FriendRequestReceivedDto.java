package com.sparta.newsfeed.friend.dto.friendRequest;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.user.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendRequestReceivedDto {
    private Long friendRequestId;
    private Long requestUserId; // 요청한 유저의 ID
    private FriendRequest.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public FriendRequestReceivedDto(FriendRequest friendRequest, UserEntity requestUser) {
        this.friendRequestId = friendRequest.getFriendRequestId();
        this.requestUserId = requestUser.getUserId();
        this.status = friendRequest.getStatus();
        this.createdAt = friendRequest.getCreatedAt();
        this.modifiedAt = friendRequest.getModifiedAt();
    }
}
