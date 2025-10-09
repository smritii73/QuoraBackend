package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.UserAdapter;
import com.example.QuoraApp.dto.UserRequestDto;
import com.example.QuoraApp.dto.UserResponseDto;
import com.example.QuoraApp.models.User;
import com.example.QuoraApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        User user = UserAdapter.toEntity(userRequestDto);
        return userRepository.save(user)
                .map(UserAdapter::toDto)
                .doOnSuccess(response -> System.out.println("User created successfully : " + response))
                .doOnError(error -> System.out.println("User creation failed : " + error.getMessage()));
    }

    @Override
    public Mono<UserResponseDto> getUserById(String id){
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User with id " + id + " not found.")))
                .map(UserAdapter::toDto)
                .doOnSuccess(response -> System.out.println("User get successfully : " + response))
                .doOnError(error -> System.out.println("User get failed : " + error.getMessage()));
    }

    @Override
    public Flux<UserResponseDto> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllBy(pageable)
                .map(UserAdapter::toDto)
                .doOnNext(response -> System.out.println("The users fetched successfully : " + response))
                .doOnError(error -> System.out.println("User fetch failed : " + error.getMessage()))
                .doOnComplete(() -> System.out.println("The user has been get successfully"));
    }

    @Override
    public Mono<UserResponseDto> incrementFollowerCount(String id){
        return userRepository.findById(id)//to increment user's following count, first we need to find the user using id
                .flatMap(user -> {
                   user.setFollowerCount(user.getFollowerCount()+1);
                   return userRepository.save(user);
                   // instead of Mono<Mono<User>> we discard inner Mono using flatMap so we get Mono<User>
                })
                .map(UserAdapter::toDto)
                .doOnSuccess(response->System.out.println("User follower increment successfully : " + response))
                .doOnError(error -> System.out.println("User follower increment failed : " + error.getMessage()));
    }


    @Override
    public Mono<UserResponseDto> incrementFollowingCount(String id){
        return userRepository.findById(id)
            .flatMap(user-> {
                user.setFollowingCount(user.getFollowingCount()+1);
                return userRepository.save(user);
            })
        .map(UserAdapter::toDto)
        .doOnSuccess(response->System.out.println("User following incremented sucessfully : " + response))
        .doOnError(error->System.out.println("User following increment failed : " + error));
    }

}