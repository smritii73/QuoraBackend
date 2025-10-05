package com.example.QuoraApp.repositories;


import com.example.QuoraApp.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends ReactiveMongoRepository<User,String> {
    Flux<User> findAllBy(Pageable pageable);
}
