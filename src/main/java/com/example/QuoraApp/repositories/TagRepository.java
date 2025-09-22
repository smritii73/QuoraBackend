package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagRepository extends ReactiveMongoRepository<Tag, String> {
    Mono<Tag> findByName(String name);
    /* The function save, findAll, findById all  these basic functions are there in ReactiveMongoRepository
    * which is the parent class of TagRepository so TagRepository extends its parents properties. */
    Flux<Tag> findAllBy(Pageable pageable);
}
