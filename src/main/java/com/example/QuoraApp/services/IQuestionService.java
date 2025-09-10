package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestDto questionRequestDto);
    public Mono<QuestionResponseDto> getQuestionById(String id);
    public Flux<QuestionResponseDto> getAllQuestions();
    public Mono<Void> deleteQuestionById(String id);
}
