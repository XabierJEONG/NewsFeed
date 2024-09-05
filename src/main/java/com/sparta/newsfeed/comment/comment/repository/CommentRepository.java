package com.sparta.newsfeed.comment.comment.repository;

import com.sparta.newsfeed.comment.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findALLUserEntityById(Long userId);

    List<Comment> findAllBoardById(Long boardId);
}
