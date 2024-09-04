package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(UserEntity user);
    Optional<Friend> findByUserIdAndFriendUserId(UserEntity userId, UserEntity friendUserId);
}
