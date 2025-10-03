package com.example.QuoraApp.services;

import com.example.QuoraApp.models.Question;
import reactor.core.publisher.Mono;

public interface IQuestionIndexService {
    Mono<Void> createQuestionIndex(Question question);
}