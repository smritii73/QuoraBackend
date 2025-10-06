package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.FollowRequestDto;
import com.example.QuoraApp.dto.FollowResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFollowService {

    public Mono<FollowResponseDto> createFollow(FollowRequestDto followRequestDto);
    public Mono<FollowResponseDto> getFollowById(String id);
    public Flux<FollowResponseDto> getAllFollows(int page, int size);
}
