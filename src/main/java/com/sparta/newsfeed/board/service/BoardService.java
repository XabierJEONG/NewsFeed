package com.sparta.newsfeed.board.service;

import com.sparta.newsfeed.board.dto.BoardRequestDto;
import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.entity.Board;
import com.sparta.newsfeed.board.repository.BoardRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto createBoard(Long userId, BoardRequestDto requestDto) {
        UserEntity findUser = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Board saveBoard = boardRepository.save(new Board(requestDto, findUser));
        return new BoardResponseDto(saveBoard);
    }

    public Page<BoardResponseDto> getBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return boards.map(BoardResponseDto::new);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long userId, Long id, BoardRequestDto requestDto) {

        UserEntity findUser = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Board findBoard = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        findBoard.update(requestDto, findUser);
        return new BoardResponseDto(findBoard);
    }

    @Transactional
    public void deleteBoard(Long userId, Long id) {
        userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        boardRepository.delete(board);
    }
}
