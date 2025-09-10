package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question,String> {

}
