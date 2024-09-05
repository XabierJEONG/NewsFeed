package com.sparta.newsfeed.comment.comment.dto;

import lombok.Getter;

@Getter
public class CommentSaveResponseDto {

    private final Long id;
    private final String comments;

    public CommentSaveResponseDto(Long id, String comments){
        this.id = id;
        this.comments = comments;
    }
}
