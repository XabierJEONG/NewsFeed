package com.sparta.newsfeed.domain.service;

import com.sparta.newsfeed.authfilter.entity.User;
import com.sparta.newsfeed.authfilter.repository.UserRepository;
import com.sparta.newsfeed.domain.dto.BoardRequestDto;
import com.sparta.newsfeed.domain.dto.BoardResponseDto;
import com.sparta.newsfeed.domain.entity.Board;
import com.sparta.newsfeed.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
//        User findUser = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Board saveBoard = boardRepository.save(new Board(requestDto));
        return new BoardResponseDto(saveBoard);
    }

    public Page<BoardResponseDto> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return boardList.map(BoardResponseDto::new);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.updateBoard(board);
        return new BoardResponseDto(board);
    }


    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        boardRepository.delete(board);
    }
}
