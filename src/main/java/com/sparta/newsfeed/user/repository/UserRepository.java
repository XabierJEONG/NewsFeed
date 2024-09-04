package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

<<<<<<< Updated upstream
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
=======
public interface UserRepository extends JpaRepository<UserEntity, Long> {
>>>>>>> Stashed changes
}
