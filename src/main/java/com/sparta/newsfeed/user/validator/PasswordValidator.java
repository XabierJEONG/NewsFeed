package com.sparta.newsfeed.user.validator;

public class PasswordValidator {

    public void validatePassword(String password) {
        if(password.length() < 8) {
            throw new IllegalArgumentException("비밀번호 8자리 이상 입력해주세요");
        }
        if(password.length() > 16) {
            throw new IllegalArgumentException("비밀번호 16자리 이하로 입력가능합니다");
        }
        if(!password.chars().anyMatch(Character::isUpperCase)) {
            throw new IllegalArgumentException("대문자 1개 이상 입력해주세요");
        }
        if(!password.chars().anyMatch(Character::isLowerCase)) {
            throw new IllegalArgumentException("소문자 1개 이상 입력해주세요");
        }
        if(!password.chars().anyMatch(Character::isDigit)) {
            throw new IllegalArgumentException("숫자를 1개 이상 입력해주세요");
        }
        if(!password.chars().anyMatch(ch -> "!@#$%^&*()_+".contains(Character.toString((char)ch)))){
            throw new IllegalArgumentException("특스문자를 1개 이상 입력해주세요");
        }
    }
}
