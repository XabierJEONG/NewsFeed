package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.LoginResponseDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import com.sparta.newsfeed.user.util.UserValidationUtil;
import com.sparta.newsfeed.user.validator.EmailValidator;
import com.sparta.newsfeed.user.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidationUtil userValidationUtil;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterResponseDto userRegiser(UserRegisterRequestDto userRegisterRequestDto) {
        userValidationUtil.checkEmailDuplication(userRegisterRequestDto.getEmail(), userRepository);
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validateEmail(userRegisterRequestDto.getEmail());

        PasswordValidator passwordValidator = new PasswordValidator();
        passwordValidator.validatePassword(userRegisterRequestDto.getPassword());

        String encodePassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());

        UserEntity user = new UserEntity(
                userRegisterRequestDto.getUsername(),
                userRegisterRequestDto.getEmail(),
                encodePassword,
                userRegisterRequestDto.getGender()
        );

        UserEntity savedUser = userRepository.save(user);

        return new UserRegisterResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getGender(),
                savedUser.getCreatedAt()
        );
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        UserEntity user = userValidationUtil.validateLogin(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword(),
                userRepository);

        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다");
        }

        String token = jwtTokenUtil.generateToken(user);
        return new LoginResponseDto("로그인이 완료되었습니다", token);
    }

    public void withdrawUser(String email, String password) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if(user.getStatus() == UserEntity.Status.WITHDRAWN){
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.setStatus(UserEntity.Status.WITHDRAWN);
        userRepository.save(user);
    }
}
