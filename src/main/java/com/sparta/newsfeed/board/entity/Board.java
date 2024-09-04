package com.sparta.newsfeed.board.entity;

import com.sparta.newsfeed.authfilter.entity.User;
import com.sparta.newsfeed.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> commentList = new ArrayList<>();


    public Board(BoardRequestDto requestDto) {
        this.content = requestDto.getContent();
//        this.user = user;

    }

    public void updateBoard(BoardRequestDto requestDto) {
        this.content = requestDto.getContent();


    }

}
