package com.sparta.newsfeed.comment.comment.controller;


import com.sparta.newsfeed.comment.comment.dto.*;
import com.sparta.newsfeed.comment.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    //댓글등록
    @PostMapping("/boards/{boardId}/comments")
    public CommentSaveResponseDto saveComment(@PathVariable Long boardId, @RequestHeader("Authorization") String token, @RequestBody CommentSaveRequestDto commentSaveRequestDto){
        return commentService.saveComment(boardId,token,commentSaveRequestDto);
    }

    //댓글등록다건조회
    @GetMapping("/boards/{boardId}/comments")
    public List<CommentSimpleResponseDto> getComments(@PathVariable Long boardId){
        return commentService.getComments(boardId);
    }

    //댓글등록단건조회
    @GetMapping("/boards/comments/{commentId}")
    public CommentDetailResponseDto getComment(@PathVariable Long commentId){
        return commentService.getComment(commentId);
    }

    //댓글수정
    @PutMapping("/boards/comments/{commentId}")
    public CommentUpdateResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto){
        return commentService.updateComment(commentId,commentUpdateRequestDto);
    }

    //댓글삭제
    @DeleteMapping("/boards/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }




}
