package com.sparta.newsfeed.user.util;

import com.sparta.newsfeed.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenUtil {

    private String secret ="AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";  // 임시로 설정해둔 값

    private Long expiration = 3600000L;

    public String generateToken(UserEntity user) {
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
