package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.entity.Users;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    //다른 사용자 또는 자신의 프로필 조회
    public Users getUser(Long viewerId, Long otherUserId) {
        Optional<Users> optionalUser = userRepository.findById(otherUserId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        Users otherUsers = optionalUser.get();
        //다른 사용자 프로필 조회할 때 민감한 정보 숨기기
        if (!viewerId.equals(otherUserId)) {
            otherUsers.setPassword(null);
            otherUsers.setEmail(null);
        }
        return otherUsers;
    }
    //사용자 정보 업데이트
    public boolean updateUser(Long userId, String currentPassword, String newPassword, String newEmail, String newUserName) {
        //데이터베이스에서 사용자 조회
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        Users currentUsers = optionalUser.get();
        //현재 비밀번호 확인
        if (!currentUsers.getPassword().equals(currentPassword)) {
            System.out.println("현재 비밀번호가 일치하지 않습니다.");
            return false;
        }

        if (currentPassword.equals(newPassword)) {
            System.out.println("새 비밀번호는 현재 비밀번호와 동일할 수 없습니다.");
            return false;
        }
        if (newPassword.length() < 8) {
            System.out.println("비밀번호는 최소 8자 이상이어야 합니다.");
            return false;
        }

        currentUsers.setPassword(newPassword);
        currentUsers.setUsername(newUserName);
        currentUsers.setEmail(newEmail);
        //변경된 사용자 정보 데이터베이스에 저장
        userRepository.save(currentUsers);
        return true;
    }
}
