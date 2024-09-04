package com.sparta.newsfeed.user.util;


import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidationUtil {
    //이메일 중복 확인 메서드
    public void checkEmailDuplication(String email, UserRepository userRepository) {
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }
    }

    //로그인 시 아이디ㅣ 비밀번호 일치 여부 확인
    public UserEntity findByEmailValidateLogin(String email, String password, UserRepository userRepository) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return userOptional.get();
    }
}
