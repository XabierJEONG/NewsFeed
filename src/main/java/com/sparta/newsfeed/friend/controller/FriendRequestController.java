package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestReceivedDto;
import com.sparta.newsfeed.friend.dto.friendRequest.requestDto.FriendRequestRequestDto;
import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestResponseDto;
import com.sparta.newsfeed.friend.service.FriendRequestService;
import com.sparta.newsfeed.user.annotation.Auth;
import com.sparta.newsfeed.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friendrequest")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    // 친구 요청
    @PostMapping
    public ResponseEntity<FriendRequestResponseDto> requestFriend(@Auth AuthUser authUser,
                                                                  @RequestBody FriendRequestRequestDto requestDto) {
        Long userId = authUser.getUserId();
        FriendRequestResponseDto responseDto = friendRequestService.requestFriend(userId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 신청한 친구 요청 리스트 조회
    @GetMapping
    public ResponseEntity<List<FriendRequestResponseDto>> inquireRequestFriend(@Auth AuthUser authUser) {
        Long userId = authUser.getUserId();
        List<FriendRequestResponseDto> responseDto = friendRequestService.inquireRequestFriend(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 받은 친구 요청 리스트 조회
    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestReceivedDto>> inquireReceivedRequests(@Auth AuthUser authUser) {
        Long userId = authUser.getUserId();
        List<FriendRequestReceivedDto> responseDto = friendRequestService.inquireReceivedRequests(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 친구 요청 거절
    @PutMapping("/{id}")
    public ResponseEntity<FriendRequestResponseDto> rejectRequestFriend(@Auth AuthUser authUser,
                                                                        @PathVariable Long id,
                                                                        @RequestBody FriendRequestRequestDto requestDto) {
        Long userId = authUser.getUserId();
        FriendRequestResponseDto responseDto = friendRequestService.rejectRequestFriend(userId, id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

    // 친구 요청 취소
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelRequestFriend(@Auth AuthUser authUser,
                                                    @PathVariable Long id) {
        Long userId = authUser.getUserId();
        friendRequestService.cancelRequestFriend(userId , id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
