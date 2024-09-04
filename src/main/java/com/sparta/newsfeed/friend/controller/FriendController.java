package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.dto.friend.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.friend.FriendResponseDto;
import com.sparta.newsfeed.friend.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // 친구수락
    @PostMapping
    public ResponseEntity<FriendResponseDto> approveFriend (@RequestHeader("Authorization") String token, @RequestBody FriendRequestDto requestDto) {
        FriendResponseDto responseDto = friendService.approveFriend(token, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 친구 목록 조회
    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> inquierFriends (@RequestHeader("Authorization") String token) {
        List<FriendResponseDto> responseDto = friendService.inquireFriends(token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 친구 삭제
    @DeleteMapping("/{friendId}")
    public ResponseEntity<FriendResponseDto> deleteFriend (@RequestHeader("Authorization") String token,
                                                           @PathVariable Long friendId) {
        friendService.deletFriend(token, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 친구 피드 조회
//    @GetMapping("/{friendId}")
//    public

}
