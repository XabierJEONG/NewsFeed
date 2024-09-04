package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(UserEntity user);
}
