package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.AnswerResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    public Mono<AnswerResponseDto> createAnswer(AnswerRequestDto answerRequestDto);
    public Mono<AnswerResponseDto> getAnswersById(String id);
    public Flux<AnswerResponseDto> getAllAnswersByQuestionId(String questionId);
}