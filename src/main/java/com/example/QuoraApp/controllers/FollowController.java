package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.FollowRequestDto;
import com.example.QuoraApp.dto.FollowResponseDto;
import com.example.QuoraApp.services.IFollowService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final IFollowService followService;

    @PostMapping
    public Mono<FollowResponseDto> createFollow(@RequestBody @Valid FollowRequestDto followRequestDto) {
        return followService.createFollow(followRequestDto)
                .doOnSuccess(responseDto -> System.out.println("Response: " + responseDto))
                .doOnError(error -> System.out.println("Error: " + error.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<FollowResponseDto> getFollowById(@PathVariable String id){
        return followService.getFollowById(id)
                .doOnSuccess(responseDto -> System.out.println("Response: " + responseDto))
                .doOnError(error -> System.out.println("Error: " + error.getMessage()));
    }

    @GetMapping
    public Flux<FollowResponseDto> getAllFollows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return followService.getAllFollows(page,size)
                .doOnNext((response-> System.out.println("Follow retrieved sucessfully")))
                .doOnError(error-> System.err.println("Error while getting all follows: " + error))
                .doOnComplete(()->System.out.println("All follows retrieved sucessfully"));
    }

    @GetMapping("/{id}/followers")
    public Flux<FollowResponseDto> getAllFollowersOfUserId(@PathVariable String id){
        return followService.getAllFollowersOfUserId(id)
                .doOnNext(response-> System.out.println("Followers retrieved sucessfully"))
                .doOnError(error -> System.err.println("Error while getting all followers: " + error))
                .doOnComplete(()->System.out.println("All followers retrieved sucessfully"));
    }

    @GetMapping("/{id}/following")
    public Flux<FollowResponseDto> getAllFollowingOfUserId(@PathVariable String id){
        return followService.getAllFollowingsOfUserId(id)
                .doOnNext(response-> System.out.println("Followings retrieved sucessfully"))
                .doOnError(error -> System.err.println("Error while getting all followings: " + error))
                .doOnComplete(()->System.out.println("All followings retrieved sucessfully"));
    }
}