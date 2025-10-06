package com.example.QuoraApp.repositories;

import com.example.QuoraApp.dto.FollowResponseDto;
import com.example.QuoraApp.models.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface FollowRepository extends ReactiveMongoRepository<Follow, String> {
    Flux<FollowResponseDto> findAllBy(Pageable pageable);
}
