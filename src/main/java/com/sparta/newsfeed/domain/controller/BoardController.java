package com.sparta.newsfeed.domain.controller;

import com.sparta.newsfeed.domain.dto.BoardRequestDto;
import com.sparta.newsfeed.domain.dto.BoardResponseDto;
import com.sparta.newsfeed.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/posts")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/posts")
    public Page<BoardResponseDto> getBoards(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return boardService.getBoards(page, size);
    }

    @PutMapping("/posts/{id}")
    public BoardResponseDto updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public void deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
}
