package com.sparta.newsfeed.user.validator;

public class PasswordValidator {
    //비밀번호 형식 확인 메서드
    public void validatePassword(String password) {
        if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, include a digit, an uppercase letter.");
        }
    }
}
