package com.sparta.newsfeed.board.service;

import com.sparta.newsfeed.board.dto.BoardRequestDto;
import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.entity.Board;
import com.sparta.newsfeed.board.repository.BoardRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        UserEntity userEntity = userRepository.findById(requestDto.getUserId()).orElseThrow();
        Board saveBoard = boardRepository.save(new Board(requestDto, userEntity));
        return new BoardResponseDto(saveBoard);
    }

    public List<BoardResponseDto> getBoards(Pageable pageable) {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        UserEntity userEntity = userRepository.findById(requestDto.getUserId()).orElseThrow();
        Board board = boardRepository.findById(id).orElseThrow();
        board.updateBoard(requestDto, userEntity);
        return new BoardResponseDto(board);
    }


    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        boardRepository.delete(board);
    }
}
