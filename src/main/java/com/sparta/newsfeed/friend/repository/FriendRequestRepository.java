package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.user.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByRequestedUserId(UserEntity userId);
    Optional<FriendRequest> findByRequestedUserIdAndReceivedUserId(UserEntity userId, UserEntity friendUserId);
    List<FriendRequest> findByReceivedUserId(UserEntity friendUserId);
    Optional<FriendRequest> findByFriendRequestId(Long friendRequestId);
}
