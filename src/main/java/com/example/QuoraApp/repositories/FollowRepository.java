package com.example.QuoraApp.repositories;

import com.example.QuoraApp.dto.FollowResponseDto;
import com.example.QuoraApp.models.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface FollowRepository extends ReactiveMongoRepository<Follow, String> {
    Flux<FollowResponseDto> findAllBy(Pageable pageable);

    // Get all followers of a user (People who follow this user)
    // Find records in follow table where followingId = userId (This user is being followed)
    Flux<FollowResponseDto> findByFollowingId(String userId);

    // Get all following of a user (People this user follows)
    // Find records in follow table where followerId = userId (This user is the follower)
    Flux<FollowResponseDto> findByFollowerId(String userId);
}
