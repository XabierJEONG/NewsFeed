package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.board.dto.BoardResponseDto;
import com.sparta.newsfeed.board.entity.Board;
import com.sparta.newsfeed.board.repository.BoardRepository;
import com.sparta.newsfeed.friend.dto.friend.requestDto.FriendRequestDto;
import com.sparta.newsfeed.friend.dto.friend.responseDto.FriendResponseDto;
import com.sparta.newsfeed.friend.entity.Friend;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.repository.FriendRepository;
import com.sparta.newsfeed.friend.repository.FriendRequestRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BoardRepository boardRepository;

    public FriendService(FriendRepository friendRepository, FriendRequestRepository friendRequestRepository, UserRepository userRepository, JwtTokenUtil jwtTokenUtil, BoardRepository boardRepository) {
        this.friendRepository = friendRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.boardRepository = boardRepository;
    }

    // 친구수락
    public FriendResponseDto approveFriend(String token, FriendRequestDto requestDto) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        UserEntity friendUser = userRepository.findById(requestDto.getFriendUserId()).orElseThrow(() ->
                new IllegalArgumentException("해당 친구 유저가 존재 하지 않습니다."));

        FriendRequest friendRequest = friendRequestRepository.findByUserIdAndFriendUserId(user, friendUser)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다."));

        if (friendRequest.getStatus() != FriendRequest.Status.WAIT) {
            throw new IllegalArgumentException("해당 친구 요청은 이미 처리 되었습니다.");
        }

        friendRequest.approve();
        friendRequestRepository.save(friendRequest);

        Friend friend = new Friend(user, friendUser);
        friendRepository.save(friend);

        return new FriendResponseDto(friend, user, friendUser);
    }

    // 친구 목록 조회
    public List<FriendResponseDto> inquireFriends(String token) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<Friend> friends = friendRepository.findByUserId(user);
        return friends.stream()
                .map(request -> {
                    UserEntity friendUser = userRepository.findById(request.getFriendUserId().getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("친구 유저가 존재하지 않습니다."));
                    return new FriendResponseDto(request, user, friendUser);
                }).toList();
    }

    // 친구 삭제
    public void deletFriend(String token, Long friendId) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        Friend friend = friendRepository.findById(friendId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 친구가 아닙니다."));

        if (!friend.getUserId().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인은 삭제할 수 없습니다.");
        }
        friendRepository.delete(friend);
    }

    // 친구 피드 조회
    public Page<BoardResponseDto> inquireFriendBoards(String token, Long friendId, Pageable pageable) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        UserEntity friendUser = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 친구 관계 확인
        boolean isFriend = friendRepository.findByUserIdAndFriendUserId(user, friendUser).isPresent() ||
                friendRepository.findByUserIdAndFriendUserId(friendUser, user).isPresent();

        if (!isFriend) {
            throw new IllegalArgumentException("게시글을 조회할 수 있는 권한이 없습니다. 친구가 아닙니다.");
        }

        // 친구인 경우 게시글 조회
        Page<Board> boardPage = boardRepository.findByUser(friendUser, pageable);

        List<BoardResponseDto> boardDtos = boardPage.getContent().stream()
                .map(BoardResponseDto::new) // 생성자를 통해 변환
                .collect(Collectors.toList());


        return new PageImpl<>(boardDtos, pageable, boardPage.getTotalElements());
    }
}
