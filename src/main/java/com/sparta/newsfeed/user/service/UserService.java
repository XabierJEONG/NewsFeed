package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.LoginResponseDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import com.sparta.newsfeed.user.util.UpdateUtil;
import com.sparta.newsfeed.user.util.UserValidationUtil;
import com.sparta.newsfeed.user.validator.EmailValidator;
import com.sparta.newsfeed.user.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidationUtil userValidationUtil;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    //화원가입 처리 메서드
    public UserRegisterResponseDto userRegiser(UserRegisterRequestDto userRegisterRequestDto) {
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

        UserEntity savedUser = userRepository.save(user);
        return new UserRegisterResponseDto(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getGender(),
                savedUser.getCreatedAt()
        );
    }

    //로그인 처리 메서드
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        //이메일 존재 여부 및 비밀번호 일치 확인
        UserEntity user = userValidationUtil.findByEmailValidateLogin(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword(),
                userRepository);
        //암호화된 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다");
        }
        //비밀번호 일치하면 토큰 생성
        String token = jwtTokenUtil.generateToken(user);
        //로그인 성공 메세지 및 토큰
        return new LoginResponseDto("로그인이 완료되었습니다", token);
    }

    //자신의 프로필 조회
    public UserEntity getMe(String token) {
        String email = jwtTokenUtil.getUserEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 다른 유저 프로필 조회
    public UserEntity getUserByEmail(String email, String loginEmail) {
        // 사용자가 자신의 프로필을 조회하려 할 때 예외 처리
        if (loginEmail.equals(email)) {
            throw new IllegalArgumentException("본인 프로필 정보는 '자신의 프로필 조회' 탭에서 조회 가능합니다.");
        }

        // 다른 사용자의 이메일을 통해 조회
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public void updateUser(String token, Map<String, Object> updateFields) {
        UserEntity user = jwtTokenUtil.getUserByToken(token);
        //UpdateUtil에서 수정을 원하는 필드만 수정하게 만들어줌
        UpdateUtil.updateUserFields(user, updateFields, passwordEncoder);
        // 변경된 사용자 정보 저장
        userRepository.save(user);
    }

    public void withdrawUser(String email, String password) {
        //이메일 조회
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        //탈퇴 여부 확인
        if (user.getStatus() == UserEntity.Status.WITHDRAWN) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }
        //비밀번호 일치 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //탈퇴 상태로 변경
        user.setStatus(UserEntity.Status.WITHDRAWN);
        userRepository.save(user);
    }

    public UserEntity getUserByToken(String token) {
        return jwtTokenUtil.getUserByToken(token);
    }
}

