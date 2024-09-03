package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.dto.friendRequest.FriendRequestRequestDto;
import com.sparta.newsfeed.friend.dto.friendRequest.FriendRequestResponseDto;
import com.sparta.newsfeed.friend.service.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendrequest")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    // 친구 추가 신청
    @PostMapping("/{userId}")
    public ResponseEntity<FriendRequestResponseDto> requestFriend(@PathVariable Long userId,
                                                                  @RequestBody FriendRequestRequestDto requestDto) {
        FriendRequestResponseDto responseDto = friendRequestService.requestFriend(userId,requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    // 친구 요청 리스트 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendRequestResponseDto>> inquireRequestFriend(@PathVariable Long userId) {
        List<FriendRequestResponseDto> reponseDto = friendRequestService.inquireRequestFriend(userId);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }
    // 친구 요청 거절
    @PutMapping("/{userId}/{friendId}")
    public ResponseEntity<FriendRequestResponseDto> rejectRequestFriend(@PathVariable Long userId,
                                                    @PathVariable Long friendId,
                                                    @RequestBody FriendRequestRequestDto requestDto) {
        FriendRequestResponseDto responseDto = friendRequestService.rejectRequestFriend(userId, friendId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }
    // 친구 요청 취소
    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<Void> cancelRequestFriend(@PathVariable Long userId,
                                                    @PathVariable Long friendId) {
        friendRequestService.cancelRequestFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
