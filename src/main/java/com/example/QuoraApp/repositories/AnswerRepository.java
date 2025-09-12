package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.models.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {
    Flux<Answer> findByQuestionId(String questionId);
}
