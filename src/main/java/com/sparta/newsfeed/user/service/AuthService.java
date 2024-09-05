package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtUtil;
import com.sparta.newsfeed.user.util.UserValidationUtil;
import com.sparta.newsfeed.user.validator.EmailValidator;
import com.sparta.newsfeed.user.validator.PasswordValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserValidationUtil userValidationUtil;

    public String userRegiser(UserRegisterRequestDto userRegisterRequestDto) {
        //이메일 중복 여부 확인
        userValidationUtil.checkEmailDuplication(userRegisterRequestDto.getEmail(), userRepository);
        //이메일 형식 검증
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validateEmail(userRegisterRequestDto.getEmail());
        //비밀번호 형식 검증
        PasswordValidator passwordValidator = new PasswordValidator();
        passwordValidator.validatePassword(userRegisterRequestDto.getPassword());
        //비밀번호 암호화 저장
        String encodePassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());
        UserEntity user = new UserEntity(
                userRegisterRequestDto.getUsername(),
                userRegisterRequestDto.getEmail(),
                encodePassword,
                userRegisterRequestDto.getGender()
        );
        UserEntity saveUser = userRepository.save(user);
        return jwtTokenUtil.generateToken(saveUser.getUserId(), saveUser.getEmail());
    }

    public String loginUser(LoginRequestDto loginRequestDto) {
        UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 계정의 유저는 없습니다."));
        return jwtTokenUtil.generateToken(user.getUserId(), user.getEmail());
    }
}
