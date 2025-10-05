package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.UserRequestDto;
import com.example.QuoraApp.dto.UserResponseDto;
import com.example.QuoraApp.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public Mono<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto)
                .doOnSuccess(response -> System.out.println("User creation successful : " + response))
                .doOnError(error -> System.out.println("User creation failed : " + error.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<UserResponseDto> getUserById(@PathVariable String id){
        return userService.getUserById(id)
                .doOnSuccess(response -> System.out.println("User get successful : " + response))
                .doOnError(error -> System.out.println("User get failed : " + error.getMessage()));
    }

    @GetMapping()
    public Flux<UserResponseDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ){
        return userService.getAllUsers(page, size)
                .doOnNext(response -> System.out.println("users retrieved : " + response))
                .doOnError(error -> System.out.println("Error while getting user : " + error))
                .doOnComplete(() -> System.out.println("All users fetched successfully"));
    }
}
