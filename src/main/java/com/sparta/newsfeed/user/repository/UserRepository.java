package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
