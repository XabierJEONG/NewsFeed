package com.sparta.newsfeed.board.controller;

import com.sparta.newsfeed.board.dto.BoardRequestDto;
import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/posts")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestHeader("Authorization") String token,
                                                        @RequestBody BoardRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.createBoard(token, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<BoardResponseDto>> getBoards(Pageable pageable) {
        Page<BoardResponseDto> responseDtoPage = boardService.getBoards(pageable);
        return ResponseEntity.ok(responseDtoPage);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@RequestHeader("Authorization") String token,
                                                        @PathVariable("id") Long id,
                                                        @RequestBody BoardRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.updateBoard(token, id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deleteBoard(@RequestHeader("Authorization") String token,
                                            @PathVariable("id") Long id) {
        boardService.deleteBoard(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
