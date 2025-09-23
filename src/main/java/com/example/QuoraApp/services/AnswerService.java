package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.AnswerAdapter;
import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.AnswerResponseDto;
import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public Mono<AnswerResponseDto> createAnswer(AnswerRequestDto answerRequestDto) {
        Answer answer = AnswerAdapter.toEntity(answerRequestDto);
        return answerRepository.save(answer)
                .map(AnswerAdapter::toDto)
                .doOnSuccess(response -> System.out.println("Answer created successfully: " + response))
                .doOnError(error-> System.out.println("Error faced : "+ error));
    }

    @Override
    public Mono<AnswerResponseDto> getAnswersById(String id) {
        Mono<Answer> getAnswersById = answerRepository.findById(id);
        return getAnswersById
                .map(AnswerAdapter::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Answer with Id : " + id + " doesn't exists")))
                .doOnSuccess(response -> System.out.println("Answer retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error faced : "+ error));
    }

    @Override
    public Flux<AnswerResponseDto> getAllAnswersByQuestionId(String questionId){
        Flux<Answer> answersByQuestionId = answerRepository.findByQuestionId(questionId);
        return answersByQuestionId
                .map(AnswerAdapter::toDto) // toDto is the static method reference of the AnswerAdapter class
                .switchIfEmpty(Flux.error(new RuntimeException("Answer with questionId : " + questionId + " doesn't exists")))
                .doOnNext(response -> System.out.println("Answer retrieved successfully: " + response))
                .doOnComplete(() -> System.out.println("Answer retrieved successfully"))
                .doOnError(error -> System.out.println("Error faced : "+ error));
    }
}