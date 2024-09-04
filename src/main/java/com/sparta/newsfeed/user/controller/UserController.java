package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.entity.Users;
import com.sparta.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    //자신의 프로필 조회
    @GetMapping("/me")
    public ResponseEntity<Users> getMe(@RequestParam Long userId) {
        Users users = userService.getUser(userId, userId);
        return ResponseEntity.ok(users);
    }

    //다른 사람의 프로필 조회
    @GetMapping("/{otherUserId}")
    public ResponseEntity<Users> getOtherUser(@PathVariable Long otherUserId, Long userId) {
        Users users = userService.getUser(userId, otherUserId);
        return ResponseEntity.ok(users);
    }

    //프로필 수정
    @PutMapping("/me")
    public ResponseEntity<String> updateMe(
            @RequestParam Long userId,
            @RequestBody UserRequestDto userRequestDto
            ) {
        boolean result = userService.updateUser(userId, userRequestDto.getCurrentPassword(),userRequestDto.getNewPassword(), userRequestDto.getUserName(), userRequestDto.getEmail());
        if (result) {
            return ResponseEntity.ok("프로필이 업데이트되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("프로필 업데이트에 실패하였습니다.");
        }
    }
}
