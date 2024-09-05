package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.friend.dto.friendRequest.requestDto.FriendRequestRequestDto;
import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestReceivedDto;
import com.sparta.newsfeed.friend.dto.friendRequest.responseDto.FriendRequestResponseDto;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.repository.FriendRequestRepository;
import com.sparta.newsfeed.user.entity.UserEntity;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    // 친구 요청
    public FriendRequestResponseDto requestFriend(Long userId, FriendRequestRequestDto requestDto) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        UserEntity friendUser = userRepository.findByUserId(requestDto.getFriendUserId()).orElseThrow(() ->
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
    public List<FriendRequestResponseDto> inquireRequestFriend(Long userId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<FriendRequest> friendRequests = friendRequestRepository.findByRequestedUserId(user);
        return friendRequests.stream()
                .map(request -> {
                    UserEntity friendUser = userRepository.findByUserId(request.getReceivedUserId().getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("친구 유저가 존재하지 않습니다."));
                    return new FriendRequestResponseDto(request, user, friendUser);
                }).toList();
    }


    // 받은 친구 요청 리스트 조회
    public List<FriendRequestReceivedDto> inquireReceivedRequests(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceivedUserId(user);
        return receivedRequests.stream()
                .map(request -> {
                    UserEntity requestUser = userRepository.findByUserId(request.getRequestedUserId().getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("요청한 유저가 존재하지 않습니다."));
                    return new FriendRequestReceivedDto(request, requestUser);
                })
                .toList();
    }

    // 친구 요청 거절
    public FriendRequestResponseDto rejectRequestFriend(Long userId, Long id, FriendRequestRequestDto requestDto) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        FriendRequest friendRequest = friendRequestRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 친구 요청이 존재하지 않습니다."));

        if (!friendRequest.getReceivedUserId().getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 요청은 본인이 받은 요청이 아닙니다.");
        }

        friendRequest.reject();
        friendRequestRepository.save(friendRequest);

        return new FriendRequestResponseDto(friendRequest, user, friendRequest.getRequestedUserId());
    }


    // 친구 요청 취소
    public void cancelRequestFriend(Long userId, Long friendRequestId) {

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
