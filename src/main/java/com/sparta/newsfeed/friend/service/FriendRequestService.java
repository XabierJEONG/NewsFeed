package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.friend.dto.friendRequest.FriendRequestRequestDto;
import com.sparta.newsfeed.friend.dto.friendRequest.FriendRequestResponseDto;
import com.sparta.newsfeed.friend.entity.FriendRequest;
import com.sparta.newsfeed.friend.repository.FriendRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
//    private final UserRepository userRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    // 친구 추가 신청
    public FriendRequestResponseDto requestFriend(Long userId, FriendRequestRequestDto requestDto) {
//        User user = userRepository.findbyId(userId.getUserId()).orElseThrow(() ->
//                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (userId.equals(requestDto.getFriendUserId())) {
            throw new IllegalArgumentException("본인을 친구로 추가할 수 없습니다.");
        }
        FriendRequest friendRequest = new FriendRequest(userId, requestDto);
        FriendRequest savefriendRequest = friendRequestRepository.save(friendRequest);

        return new FriendRequestResponseDto(savefriendRequest);
    }

    // 친구 요청 리스트 조회
    public List<FriendRequestResponseDto> inquireRequestFriend(Long userId) {
        return friendRequestRepository.findByUserId(userId).stream().map(FriendRequestResponseDto::new).toList();
    }

    // 친구 요청 거절
    public FriendRequestResponseDto rejectRequestFriend(Long userId, Long friendId, FriendRequestRequestDto requestDto) {
       FriendRequest friendRequest = friendRequestRepository.findByUserIdAndFriendUserId(userId, friendId).orElseThrow(() ->
               new IllegalArgumentException("선택한 유저의 친구 신청 목록에 없는 유저입니다."));
       friendRequest.reject();
       friendRequestRepository.save(friendRequest);
       return new FriendRequestResponseDto(friendRequest);
    }

    // 친구 요청 취소
    public void cancelRequestFriend(Long userId, Long friendId) {
        FriendRequest friendRequest = friendRequestRepository.findByUserIdAndFriendUserId(userId, friendId).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저의 친구 신청 목록에 없는 유저입니다."));
        friendRequestRepository.delete(friendRequest);
    }
}
