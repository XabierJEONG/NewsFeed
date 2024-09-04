package com.sparta.newsfeed.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    @Column(nullable = false, name = "username")
    private String username;
    @Column(nullable = false, unique = true, name = "email" )
    private String email;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(nullable = false, name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = true, name = "introduce")
    private String introduce;
    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "modifiedAt")
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    public UserEntity(String username, String email, String password, Gender gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.modifiedAt = LocalDateTime.now();
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum Status{
        ACTIVE, WITHDRAWN
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
