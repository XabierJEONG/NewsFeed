package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestReceivedDto;
import com.sparta.newsfeed.friend.dto.friendRequest.requestDto.FriendRequestRequestDto;
import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestResponseDto;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.repository.FriendRequestRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // 친구 요청
    public FriendRequestResponseDto requestFriend(String token, FriendRequestRequestDto requestDto) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        UserEntity friendUser = userRepository.findById(requestDto.getFriendUserId()).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        if (userId.equals(friendUser.getUserId())) {
            throw new IllegalArgumentException("본인을 친구로 추가할 수 없습니다.");
        }

        Optional<FriendRequest> checkAlreadyRequest = friendRequestRepository.findByRequestedUserIdAndReceivedUserId(user, friendUser);
        if (checkAlreadyRequest.isPresent()) {
            throw new IllegalArgumentException("해당 친구는 이미 요청을 보냈습니다.");
        }

        FriendRequest friendRequest = new FriendRequest(user, friendUser, requestDto);
        FriendRequest savefriendRequest = friendRequestRepository.save(friendRequest);

        return new FriendRequestResponseDto(savefriendRequest, user, friendUser);
    }

    // 신청한 친구 요청 리스트 조회
    public List<FriendRequestResponseDto> inquireRequestFriend(String token) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<FriendRequest> friendRequests = friendRequestRepository.findByRequestedUserId(user);
        return friendRequests.stream()
                .map(request -> {
                    UserEntity friendUser = userRepository.findById(request.getReceivedUserId().getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("친구 유저가 존재하지 않습니다."));
                    return new FriendRequestResponseDto(request, user, friendUser);
                }).toList();
    }


    // 받은 친구 요청 리스트 조회
    public List<FriendRequestReceivedDto> inquireReceivedRequests(String token) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceivedUserId(user);
        return receivedRequests.stream()
                .map(request -> {
                    UserEntity requestUser = userRepository.findById(request.getRequestedUserId().getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("요청한 유저가 존재하지 않습니다."));
                    return new FriendRequestReceivedDto(request, requestUser);
                })
                .toList();
    }

    // 친구 요청 거절
    public FriendRequestResponseDto rejectRequestFriend(String token, Long friendRequestId, FriendRequestRequestDto requestDto) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow(() ->
                new IllegalArgumentException("해당 친구 요청이 존재하지 않습니다."));

        if (!friendRequest.getReceivedUserId().getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 요청은 본인이 받은 요청이 아닙니다.");
        }

        friendRequest.reject();
        friendRequestRepository.save(friendRequest);

        return new FriendRequestResponseDto(friendRequest, user, friendRequest.getRequestedUserId());
    }


    // 친구 요청 취소
    public void cancelRequestFriend(String token, Long friendRequestId) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow(() ->
                new IllegalArgumentException("해당 친구 요청이 존재하지 않습니다."));

        if (!friendRequest.getRequestedUserId().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인의 친구 요청이 아닙니다.");
        }

        friendRequestRepository.delete(friendRequest);
    }
}
