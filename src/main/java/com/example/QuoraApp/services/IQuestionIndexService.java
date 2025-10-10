package com.example.QuoraApp.services;

import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionIndexService {
    public Mono<Void> createQuestionIndex(Question question);
    public Flux<QuestionElasticDocument> searchQuestionByElasticSearch(String query);
    public Mono<Void> deleteAllQuestions();
    public Mono<Void> deleteQuestionById(String id);
}