package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.service.BoardService;
import com.sparta.newsfeed.friend.dto.friend.requestDto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.friend.responseDto.FriendResponseDto;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.service.FriendService;
import com.sparta.newsfeed.user.annotation.Auth;
import com.sparta.newsfeed.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    // 친구 수락
    @PostMapping
    public ResponseEntity<FriendResponseDto> approveFriend(@Auth AuthUser authUser,
                                                           @RequestBody FriendRequestDto friendRequestDto) {
        FriendResponseDto responseDto = friendService.approveFriend(authUser, friendRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 친구 목록 조회
    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> inquireFriends(@Auth AuthUser authUser) {
        Long userId = authUser.getUserId();
        List<FriendResponseDto> responseDto = friendService.inquireFriends(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 친구 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@Auth AuthUser authUser,
                                             @PathVariable Long id) {
        Long userId = authUser.getUserId();
        friendService.deleteFriend(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 친구 피드 조회
    @GetMapping("/{friendId}")
    public ResponseEntity<Page<BoardResponseDto>> inquireFriendBoards(@Auth AuthUser authUser,
                                                                      @PathVariable Long friendId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Long userId = authUser.getUserId();
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardResponseDto> boards = friendService.inquireFriendBoards(userId, friendId, pageable);
        return ResponseEntity.ok(boards);
    }
}
