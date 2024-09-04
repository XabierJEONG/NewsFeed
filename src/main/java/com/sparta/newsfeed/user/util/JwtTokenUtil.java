package com.sparta.newsfeed.user.util;

import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
public class JwtTokenUtil {
    //secretKey 무작위 임시 생성
    private String secret ="AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    //토큰 만료 시간 1시간
    private Long expiration = 3600000L;

    @Autowired
    private UserRepository userRepository;

    //jwt토큰 생성 메서드
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getUserId()) // userId 클레임추가(진호)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    //claim 추출 메서드
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new IllegalArgumentException("잘못된 JWT 형식입니다.");
        } catch (Exception e) {
            throw new IllegalArgumentException("JWT 처리 중 오류가 발생했습니다.");
        }
    }
    //userEmail 추출 메서드(현수) 개인정보 조회 시 필요
    public String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    // JWT 토큰에서 사용자 정보 조회
    public UserEntity getUserByToken(String token) {
        // 토큰에서 이메일 추출
        String email = getUserEmailFromToken(token);

        // 이메일로 사용자 조회
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // userId 추출메서드(진호)
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);// Long타입으로 추출
    }
}
