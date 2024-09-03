package com.sparta.newsfeed.friend.entity;

import com.sparta.newsfeed.friend.dto.friendRequest.FriendRequestRequestDto;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", nullable = false)
    @Column(name = "userId", nullable = false)
    private Long userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "friendUserId", nullable = false)
    @Column(name = "friendUserId", nullable = false)
    private Long friendUserId;

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

    public FriendRequest(Long userId, FriendRequestRequestDto requestDto) {
        this.userId = userId;
        this.friendUserId = requestDto.getFriendUserId();
        this.status = Status.WAIT;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = Status.REJECT;
    }

}
