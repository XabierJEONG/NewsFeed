package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByUserId(UserEntity userId);
    Optional<FriendRequest> findByUserIdAndFriendUserId(UserEntity userId, UserEntity friendUserId);
    List<FriendRequest> findByFriendUserId(UserEntity friendUserId);
}
