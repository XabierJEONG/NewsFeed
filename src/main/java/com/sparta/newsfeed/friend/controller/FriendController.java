package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.service.BoardService;
import com.sparta.newsfeed.friend.dto.friend.responseDto.FriendResponseDto;
import com.sparta.newsfeed.friend.service.FriendService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService, BoardService boardService) {
        this.friendService = friendService;
    }

    // 친구 수락
    @PostMapping("/{requestedUserId}/{friendRequestId}")
    public ResponseEntity<FriendResponseDto> approveFriend(@RequestHeader("Authorization") String token,
                                                           @PathVariable Long requestedUserId,
                                                           @PathVariable Long friendRequestId) {
        FriendResponseDto responseDto = friendService.approveFriend(token, requestedUserId, friendRequestId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 친구 목록 조회
    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> inquierFriends(@RequestHeader("Authorization") String token) {
        List<FriendResponseDto> responseDto = friendService.inquireFriends(token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 친구 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@RequestHeader("Authorization") String token,
                                             @PathVariable Long id) {
        friendService.deleteFriend(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 친구 피드 조회
    @GetMapping("/{friendId}")
    public ResponseEntity<Page<BoardResponseDto>> inquireFriendBoards(@RequestHeader("Authorization") String token,
                                                                      @PathVariable Long friendId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardResponseDto> boards = friendService.inquireFriendBoards(token, friendId, pageable);
        return ResponseEntity.ok(boards);
    }
}
