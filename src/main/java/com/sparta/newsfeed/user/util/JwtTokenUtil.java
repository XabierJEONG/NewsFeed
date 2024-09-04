package com.sparta.newsfeed.user.util;

import com.sparta.newsfeed.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
public class JwtTokenUtil {
    //secretKey 무작위 임시 생성
    private String secret ="AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    //토큰 만료 시간 1시간
    private Long expiration = 3600000L;
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
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // userId 추출메서드(진호)
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);// Long타입으로 추출
    }
}
