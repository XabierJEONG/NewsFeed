package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Void> userRegister(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        String bearerToken = authService.userRegiser(userRegisterRequestDto);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        String bearerToken = authService.loginUser(loginRequestDto);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .build();
    }
}
