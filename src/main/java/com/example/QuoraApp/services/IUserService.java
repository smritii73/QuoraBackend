package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.UserRequestDto;
import com.example.QuoraApp.dto.UserResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    public Mono<UserResponseDto> createUser(UserRequestDto userRequestDto);
    public Mono<UserResponseDto> getUserById(String id);
    public Flux<UserResponseDto> getAllUsers(int page, int size);
    public Mono<UserResponseDto> incrementFollowerCount(String id);
    public Mono<UserResponseDto> incrementFollowingCount(String id);
}
