package com.sparta.newsfeed.friend.dto.friendRequest.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestRequestDto {
    private Long userId;
    private Long friendUserId;
}
