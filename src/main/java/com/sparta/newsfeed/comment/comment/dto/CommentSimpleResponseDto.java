package com.sparta.newsfeed.comment.comment.dto;

import lombok.Getter;

@Getter
public class CommentSimpleResponseDto {

    private final Long id;
    private final String comments;

    public CommentSimpleResponseDto(Long id, String comments){
        this.id =id;
        this.comments = comments;
    }

}
