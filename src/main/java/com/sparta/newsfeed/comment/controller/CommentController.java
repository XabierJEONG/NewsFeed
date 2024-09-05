package com.sparta.newsfeed.comment.controller;


import com.sparta.newsfeed.comment.dto.*;
import com.sparta.newsfeed.comment.service.CommentService;
import com.sparta.newsfeed.user.annotation.Auth;
import com.sparta.newsfeed.user.dto.AuthUser;
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
    public CommentSaveResponseDto saveComment(@PathVariable Long boardId,@Auth AuthUser authUser, @RequestBody CommentSaveRequestDto commentSaveRequestDto){
        return commentService.saveComment(boardId,authUser,commentSaveRequestDto);
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
