package com.sparta.newsfeed.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String username;
    private String password;
    private String email;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    private Gender gender;
    private String introduce;


    public Users(String username, String password, String email, Gender gender, String introduce) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.introduce = introduce;

    }
}
