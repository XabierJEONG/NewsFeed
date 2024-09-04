package com.sparta.newsfeed.user.service;

<<<<<<< Updated upstream
import com.sparta.newsfeed.user.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserRegisterRequestDto;
import com.sparta.newsfeed.user.dto.response.LoginResponseDto;
import com.sparta.newsfeed.user.dto.response.UserRegisterResponseDto;
=======
>>>>>>> Stashed changes
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import com.sparta.newsfeed.user.util.UserValidationUtil;
import com.sparta.newsfeed.user.validator.EmailValidator;
import com.sparta.newsfeed.user.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
<<<<<<< Updated upstream
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
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다");
=======
    //다른 사용자 또는 자신의 프로필 조회
    public UserEntity getUser(Long viewerId, Long otherUserId) {
        Optional<UserEntity> optionalUser = userRepository.findById(otherUserId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        UserEntity otherUserEntity = optionalUser.get();
        //다른 사용자 프로필 조회할 때 민감한 정보 숨기기
        if (!viewerId.equals(otherUserId)) {
            otherUserEntity.setPassword(null);
            otherUserEntity.setEmail(null);
        }
        return otherUserEntity;
    }
    //사용자 정보 업데이트
    public boolean updateUser(Long userId, String currentPassword, String newPassword, String newEmail, String newUserName) {
        //데이터베이스에서 사용자 조회
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        UserEntity currentUserEntity = optionalUser.get();
        //현재 비밀번호 확인
        if (!currentUserEntity.getPassword().equals(currentPassword)) {
            System.out.println("현재 비밀번호가 일치하지 않습니다.");
            return false;
>>>>>>> Stashed changes
        }
        //비밀번호 일치하면 토큰 생성
        String token = jwtTokenUtil.generateToken(user);
        //로그인 성공 메세지 및 토큰
        return new LoginResponseDto("로그인이 완료되었습니다", token);
    }

    public void withdrawUser(String email, String password) {
        //이메일 조회
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        //탈퇴 여부 확인
        if(user.getStatus() == UserEntity.Status.WITHDRAWN){
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }
        //비밀번호 일치 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
<<<<<<< Updated upstream
        //탈퇴 상태로 변경
        user.setStatus(UserEntity.Status.WITHDRAWN);
        userRepository.save(user);
=======

        currentUserEntity.setPassword(newPassword);
        currentUserEntity.setUsername(newUserName);
        currentUserEntity.setEmail(newEmail);
        //변경된 사용자 정보 데이터베이스에 저장
        userRepository.save(currentUserEntity);
        return true;
>>>>>>> Stashed changes
    }
}
