package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.FollowAdapter;
import com.example.QuoraApp.dto.FollowRequestDto;
import com.example.QuoraApp.dto.FollowResponseDto;
import com.example.QuoraApp.dto.UserResponseDto;
import com.example.QuoraApp.models.Follow;
import com.example.QuoraApp.repositories.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService{

    private final FollowRepository followRepository;
    private final UserService userService;


    @Override
    public Mono<FollowResponseDto> createFollow(FollowRequestDto followRequestDto){
        // Validate that user is not following themselves
        if (followRequestDto.getFollowerId().equals(followRequestDto.getFollowingId())) {
            return Mono.error(new IllegalArgumentException("Cannot follow yourself"));
        }
        Follow follow = FollowAdapter.toEntity(followRequestDto);
        return followRepository.save(follow)
                .flatMap(savedFollow -> {
                    Mono<UserResponseDto> follower = userService.incrementFollowingCount(follow.getFollowerId());
                    Mono<UserResponseDto> followedUser = userService.incrementFollowerCount(follow.getFollowingId());
                    return Mono.zip(followedUser, follower)
                            .thenReturn(savedFollow);
                })
                .map(FollowAdapter::toDto)
                .doOnError(error -> System.out.println("Error while follow creation : " + error))
                .doOnSuccess(response -> System.out.println("Follow created sucessfully" + response));
    }

    @Override
    public Mono<FollowResponseDto> getFollowById(String id){
        return followRepository.findById(id)
                .map(FollowAdapter::toDto)
                .doOnError(error -> System.out.println("Error while follow retrieval : " + error))
                .doOnSuccess(response -> System.out.println("Follow retrieved sucessfully" + response));
    }

    @Override
    public Flux<FollowResponseDto> getAllFollows(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return followRepository.findAllBy(pageable)
                .doOnNext(response-> System.out.println("Follow retrieved sucessfully"))
                .doOnComplete(()->System.out.println("All follow got successfully"))
                .doOnError(error->System.out.println("Error while follow retrieval : " + error));
    }

    @Override
    public Flux<FollowResponseDto> getAllFollowersOfUserId(String userId){
        return followRepository.findByFollowingId(userId)
                .doOnNext(response-> System.out.println("Followers retrieved sucessfully"))
                .doOnError(error -> System.out.println("Error while followers retrieval : " + error))
                .doOnComplete(()->System.out.println("All followers got successfully"));
    }

    @Override
    public Flux<FollowResponseDto> getAllFollowingsOfUserId(String userId){
        return followRepository.findByFollowerId(userId)
                .doOnNext(response-> System.out.println("Following retrieved sucessfully"))
                .doOnError(error-> System.out.println("Error while followings retrieval : " + error))
                .doOnComplete(() -> System.out.println("All the following retrieved successfully"));
    }
}
