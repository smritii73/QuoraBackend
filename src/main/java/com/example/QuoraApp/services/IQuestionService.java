package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.models.TagFilterType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface IQuestionService {
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestDto questionRequestDto);
    public Mono<QuestionResponseDto> getQuestionById(String id);
    public Flux<QuestionResponseDto> getAllQuestions();
    public Mono<Void> deleteQuestionById(String id);
    public Flux<QuestionResponseDto> searchQuestions(String searchTerm,Integer offset,Integer pageSize);
    public Flux<QuestionResponseDto> searchQuestionByCursor(String cursor, int size);
    public Flux<QuestionResponseDto> getQuestionsByTags(List<String> tagIds, TagFilterType tagFilterType, int page, int size);
    public Flux<QuestionResponseDto> getQuestionsByTagId(String tagId, int page, int size);
    public Flux<QuestionResponseDto> getQuestionsByAnyTags(List<String> tagIds, int page, int size);
    public Flux<QuestionResponseDto> getQuestionsByAllTags(List<String> tagIds, int page, int size);
}