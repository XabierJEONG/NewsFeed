package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<Friend, Long> {
}
