package com.sparta.newsfeed.friend.dto.friend.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDto {
    private Long userId;
    private Long friendUserId;
}
