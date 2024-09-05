package com.sparta.newsfeed.board.controller;

import com.sparta.newsfeed.board.dto.BoardRequestDto;
import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.service.BoardService;
import com.sparta.newsfeed.user.annotation.Auth;
import com.sparta.newsfeed.user.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/posts")
    public ResponseEntity<BoardResponseDto> createBoard(@Auth AuthUser authUser,
                                                        @RequestBody BoardRequestDto requestDto) {
        Long userId = authUser.getUserId();
        BoardResponseDto responseDto = boardService.createBoard(userId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<BoardResponseDto>> getBoards(Pageable pageable) {
        Page<BoardResponseDto> responseDtoPage = boardService.getBoards(pageable);
        return ResponseEntity.ok(responseDtoPage);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@Auth AuthUser authUser,
                                                        @PathVariable("id") Long id,
                                                        @RequestBody BoardRequestDto requestDto) {
        Long userId = authUser.getUserId();
        BoardResponseDto responseDto = boardService.updateBoard(userId, id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deleteBoard(@Auth AuthUser authUser,
                                            @PathVariable("id") Long id) {
        Long userId = authUser.getUserId();
        boardService.deleteBoard(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
