package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.LikeResponseDto;
import com.example.QuoraApp.services.ILikeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final ILikeService likeService;

    @PostMapping
    public Mono<LikeResponseDto> createLike(@RequestBody @Valid LikeRequestDto likeRequestDto){
        return likeService.createLike(likeRequestDto)
                .doOnSuccess(response -> System.out.println("Like created successfully: " + response))
                .doOnError(error -> System.out.println("Like creation failed: " + error));
    }

    @GetMapping("/{id}")
    public Mono<LikeResponseDto> getLikeById(@PathVariable String id){
        // we give @PathVariable for whenever when pass id in curly braces{} and @RequestBody for PostMapping
        return likeService.getLikeById(id)
                .doOnSuccess(response -> System.out.println("Like get successfully: " + response))
                .doOnError(error -> System.out.println("Like get failed: " + error));

    }
}
