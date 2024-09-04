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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserValidationUtil userValidationUtil;
    private JwtTokenUtil jwtTokenUtil;

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
    public ResponseEntity<UserEntity> getMe (@RequestParam Long userId){
        UserEntity users = userService.getUser(userId, userId);
        return ResponseEntity.ok(users);
    }

    //다른 사람의 프로필 조회
    @GetMapping("/{otherUserId}")
    public ResponseEntity<UserEntity> getOtherUser (@PathVariable Long otherUserId, Long userId){
        UserEntity userEntity = userService.getUser(userId, otherUserId);
        return ResponseEntity.ok(userEntity);
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

