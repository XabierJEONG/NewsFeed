package com.sparta.newsfeed.friend.entity;

import com.sparta.newsfeed.friend.dto.friendRequest.requestDto.FriendRequestRequestDto;
import com.sparta.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "FriendRequest")
@NoArgsConstructor
public class FriendRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendrequestId")
    private Long friendRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestedUserId", nullable = false)
    private UserEntity requestedUserId; // 신청한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receivedUserId", nullable = false)
    private UserEntity receivedUserId; // 신청받은 사용자

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;

    public enum Status {
        APPROVAL, WAIT, REJECT
    }

    public FriendRequest(UserEntity user, UserEntity friendUser, FriendRequestRequestDto requestDto) {
        this.requestedUserId = user;
        this.receivedUserId = friendUser;
        this.status = Status.WAIT;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = Status.REJECT;
    }
    public void approve() {
        this.status = Status.APPROVAL;
    }

}
