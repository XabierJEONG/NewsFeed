package com.sparta.newsfeed.comment.comment.dto;

import lombok.Getter;

@Getter
public class CommentDetailResponseDto {

    private final Long id;
    private final String comments;

    public CommentDetailResponseDto(Long id,String comments){
        this.id = id;
        this.comments =comments;
    }
}
