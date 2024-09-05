package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.board.entity.Board;
import com.sparta.newsfeed.board.entity.Timestamped;
import com.sparta.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;


    public Comment(String comments,UserEntity user, Board board){
        this.comments = comments;
        this.user = user;
        this.board = board;
    }

    public void update(String comments){
        this.comments = comments;
    }
}
