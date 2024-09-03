package com.sparta.newsfeed.user.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserValidationUtil userValidationUtil;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public UserRegisterResponseDto userRegiser(UserRegisterRequestDto userRegisterRequestDto) {
        userValidationUtil.checkEmailDuplication(userRegisterRequestDto.getEmail(), userRepository);
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validateEmail(userRegisterRequestDto.getEmail());

        PasswordValidator passwordValidator = new PasswordValidator();
        passwordValidator.validatePassword(userRegisterRequestDto.getPassword());


        UserEntity user = new UserEntity(
                userRegisterRequestDto.getUsername(),
                userRegisterRequestDto.getEmail(),
                userRegisterRequestDto.getPassword(),
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
        String token = jwtTokenUtil.generateToken(user);

        return new LoginResponseDto("로그인이 완료되었습니다", token);
    }
}
