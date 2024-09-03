package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByUserId(Long userId);
    Optional<FriendRequest> findByUserIdAndFriendUserId(Long userId, Long friendUserId);
}
