package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestReceivedDto;
import com.sparta.newsfeed.friend.dto.friendRequest.requestDto.FriendRequestRequestDto;
import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestResponseDto;
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

    // 친구 요청
    @PostMapping
    public ResponseEntity<FriendRequestResponseDto> requestFriend(@RequestHeader("Authorization") String token,
                                                                  @RequestBody FriendRequestRequestDto requestDto) {
        FriendRequestResponseDto responseDto = friendRequestService.requestFriend(token, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 신청한 친구 요청 리스트 조회
    @GetMapping
    public ResponseEntity<List<FriendRequestResponseDto>> inquireRequestFriend(@RequestHeader("Authorization") String token) {
        List<FriendRequestResponseDto> responseDto = friendRequestService.inquireRequestFriend(token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 받은 친구 요청 리스트 조회
    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestReceivedDto>> inquireReceivedRequests(@RequestHeader("Authorization") String token) {
        List<FriendRequestReceivedDto> responseDto = friendRequestService.inquireReceivedRequests(token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 친구 요청 거절
    @PutMapping("/{friendRequestId}")
    public ResponseEntity<FriendRequestResponseDto> rejectRequestFriend(@RequestHeader("Authorization") String token,
                                                                        @PathVariable Long friendRequestId,
                                                                        @RequestBody FriendRequestRequestDto requestDto) {
        FriendRequestResponseDto responseDto = friendRequestService.rejectRequestFriend(token, friendRequestId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

    // 친구 요청 취소
    @DeleteMapping("/{friendRequestId}")
    public ResponseEntity<Void> cancelRequestFriend(@RequestHeader("Authorization") String token,
                                                    @PathVariable Long friendRequestId) {
        friendRequestService.cancelRequestFriend(token, friendRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
