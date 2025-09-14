package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Tag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TagRepository extends ReactiveMongoRepository<Tag, String> {
}
