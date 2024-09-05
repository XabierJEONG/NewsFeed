package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.LoginResponseDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.UpdateUtil;
import com.sparta.newsfeed.user.util.UserValidationUtil;
import com.sparta.newsfeed.user.validator.EmailValidator;
import com.sparta.newsfeed.user.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidationUtil userValidationUtil;
    private final PasswordEncoder passwordEncoder;

    //자신의 프로필 조회
    public UserEntity getMe(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 다른 유저 프로필 조회
    public UserEntity getUserByEmail(String email, String loginEmail) {
        // 사용자가 자신의 프로필을 조회하려 할 때 예외 처리
        if (loginEmail.equals(email)) {
            throw new IllegalArgumentException("본인 프로필 정보는 '자신의 프로필 조회' 탭에서 조회 가능합니다.");
        }

        // 다른 사용자의 이메일을 통해 조회
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public void updateUser(Long userId, Map<String, Object> updateFields) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        //UpdateUtil에서 수정을 원하는 필드만 수정하게 만들어줌
        UpdateUtil.updateUserFields(user, updateFields, passwordEncoder);
        // 변경된 사용자 정보 저장
        userRepository.save(user);
    }

    public void withdrawUser(String email, String password) {
        //이메일 조회
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        //탈퇴 여부 확인
        if (user.getStatus() == UserEntity.Status.WITHDRAWN) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }
        //비밀번호 일치 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //탈퇴 상태로 변경
        user.setStatus(UserEntity.Status.WITHDRAWN);
        userRepository.save(user);
    }
}

