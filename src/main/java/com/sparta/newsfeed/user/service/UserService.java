package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
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


    public UserRegisterResponseDto userRegiser(UserRegisterRequestDto userRegisterRequestDto) {
        if(userRepository.existsByEmail(userRegisterRequestDto.getEmail())){
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다!");
        }
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
                savedUser.getPassword(),
                savedUser.getGender(),
                savedUser.getCreatedAt()
        );

    }
}
