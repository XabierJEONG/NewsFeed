package com.sparta.newsfeed.user.util;


import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidationUtil {

    public void checkEmailDuplication(String email, UserRepository userRepository) {
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }
    }

    //로그인 시 아이부 일치여부?
    public UserEntity validateLogin(String email, String password, UserRepository userRepository) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return userOptional.get();
    }
}
