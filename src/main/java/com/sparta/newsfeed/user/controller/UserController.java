package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        try{
            UserRegisterResponseDto userRegisterResponseDto = userService.userRegiser(userRegisterRequestDto);
            return ResponseEntity.ok("사용자가 등록되었습니다.");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
