package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findAllBy(Pageable pageable);
}
