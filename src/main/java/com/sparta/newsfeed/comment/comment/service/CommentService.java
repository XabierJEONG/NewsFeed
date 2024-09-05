package com.sparta.newsfeed.comment.comment.service;

import com.sparta.newsfeed.board.entity.Board;
import com.sparta.newsfeed.board.repository.BoardRepository;
import com.sparta.newsfeed.comment.comment.dto.*;
import com.sparta.newsfeed.comment.comment.entity.Comment;
import com.sparta.newsfeed.comment.comment.repository.CommentRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;



    //댓글등록
    @Transactional
    public CommentSaveResponseDto saveComment(Long boardId, String token, CommentSaveRequestDto commentSaveRequestDto) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        log.info("userId => {}", userId);
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new NullPointerException("사용자는 없습니다"));
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("게시물이 없습니다."));

        Comment newComment = new Comment(commentSaveRequestDto.getComments(),user, board);
        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(savedComment.getId(),savedComment.getComments());
    }

    //댓글다건조회
    public List<CommentSimpleResponseDto> getComments(Long boardId) {
        List<Comment> commentList = commentRepository.findAllBoardById(boardId);

        List<CommentSimpleResponseDto> dtoList = new ArrayList<>();
        for(Comment comment : commentList){
            CommentSimpleResponseDto dto = new CommentSimpleResponseDto(comment.getId(),comment.getComments());
            dtoList.add(dto);
        }
        return dtoList;

    }

    //댓글단건조회
    public CommentDetailResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("댓글은 없습니다."));

        return new CommentDetailResponseDto(comment.getId(),comment.getComments());
    }

    //댓글수정
    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("댓글은 없습니다."));
        comment.update(commentUpdateRequestDto.getContents());

        return new CommentUpdateResponseDto(comment.getId(),comment.getComments());
    }

    //댓글삭제
    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("댓글은 없습니다."));
        commentRepository.deleteById(commentId);

    }
}
