package com.sparta.newsfeed.user.controller;


import com.sparta.newsfeed.user.dto.request.WithdrawRequestDto;
import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.LoginResponseDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.service.UserService;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import com.sparta.newsfeed.user.util.UserValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserValidationUtil userValidationUtil;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        try {
            //회원가입
            UserRegisterResponseDto userRegisterResponseDto = userService.userRegiser(userRegisterRequestDto);
            return ResponseEntity.ok("사용자가 등록되었습니다.");
        }
        //회원가입 실패 시
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        //로그인
        return userService.loginUser(loginRequestDto);
    }


    //자신의 프로필 조회
    @GetMapping("/me")
    public ResponseEntity<UserEntity> getMe (@RequestHeader("Authorization")String token){
        UserEntity user = userService.getUserByToken(token);
        return ResponseEntity.ok(user);
    }

    //다른 사람의 프로필 조회
    //모든 사람이 확인하면 안되므로 토큰으로 인증된(로그인된) 사용자만 확인할 수 있도록함
    @GetMapping("/otherUser")
    public ResponseEntity<UserEntity> getOtherUser (@RequestHeader("Authorization")String token, @RequestParam String email){
        // JWT 토큰에서 로그인한 사용자의 이메일 추출
        String loginEmail = jwtTokenUtil.getUserEmailFromToken(token);

        // 다른 사용자의 정보를 이메일로 조회
        UserEntity otherUser = userService.getUserByEmail(email, loginEmail);

        // 로그인한 사용자와 다른 사용자일 때 민감한 정보 제거
        otherUser.setPassword(null);  // 비밀번호는 노출되지 않게 null 처리

        return ResponseEntity.ok(otherUser);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> updateFields) {

        userService.updateUser(token,updateFields);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser (HttpServletRequest request){
        //로그아웃 (세션 기반)
        request.getSession().invalidate();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdrawUser (@RequestBody WithdrawRequestDto withdrawRequestDto){
        try {
            userService.withdrawUser(withdrawRequestDto.getEmail(), withdrawRequestDto.getPassword());
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다");
        }
        //회원 탈퇴 시
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

