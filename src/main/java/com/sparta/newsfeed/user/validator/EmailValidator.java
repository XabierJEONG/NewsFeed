package com.sparta.newsfeed.user.validator;

import java.util.regex.Pattern;

public class EmailValidator {
    //이메일 형식
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    //이메일 형식 확인 메서드
    public void validateEmail(String email) {
        if(email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
