package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.LikeAdapter;
import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.LikeResponseDto;
import com.example.QuoraApp.models.Like;
import com.example.QuoraApp.repositories.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {

    private final LikeRepository likeRepository;

    @Override
    public Mono<LikeResponseDto> createLike(LikeRequestDto likeRequestDto){
        Like like = LikeAdapter.toEntity(likeRequestDto);
        return likeRepository.save(like)
                .map(LikeAdapter::toDto)
                .doOnSuccess(response -> System.out.println("Like created successfully: " + response))
                .doOnError(error -> System.out.println("Like creation failed: " + error));
    }

    @Override
    public Mono<LikeResponseDto> getLikeById(String id){
        return likeRepository.findById(id)
                .map(LikeAdapter::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Like with id : " + id + " doesn't exists")))
                .doOnSuccess(response -> System.out.println("Like get successfully: " + response))
                .doOnError(error -> System.out.println("Like get failed: " + error));
    }
}