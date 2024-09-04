package com.sparta.newsfeed.board.repository;

import com.sparta.newsfeed.board.entity.Board;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Board> findByUser(UserEntity user, Pageable pageable);
}
