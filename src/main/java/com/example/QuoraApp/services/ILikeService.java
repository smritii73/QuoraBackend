package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.LikeResponseDto;
import reactor.core.publisher.Mono;

public interface ILikeService {
    public Mono<LikeResponseDto> createLike(LikeRequestDto likeRequestDto);
    public Mono<LikeResponseDto> getLikeById(String id);
}
