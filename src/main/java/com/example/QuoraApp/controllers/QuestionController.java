package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.services.IQuestionService;
import com.example.QuoraApp.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;

    @PostMapping
    public Mono<QuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto questionRequestDto) {
        return questionService.createQuestion(questionRequestDto)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error));
    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDto> getQuestionById(@PathVariable String id) {
        return this.questionService.getQuestionById(id)
                .doOnSuccess(response -> System.out.println("Question retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error getting question: " + error));
    }

    @GetMapping
    public Flux<QuestionResponseDto> getAllQuestions() {
        return this.questionService.getAllQuestions()
                .doOnNext(response -> System.out.println("Questions retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error getting all questions: " + error));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id) {
        return this.questionService.deleteQuestionById(id)
                .doOnSuccess(response -> System.out.println("Question deleted successfully: " + response))
                .doOnError(error -> System.out.println("Error deleting question: " + error));
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDto> searchQuestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "100") int pageSize
    ){
        return this.questionService.searchQuestions(query, offset, pageSize)
                .doOnComplete(() -> System.out.println("Search completed successfully"))
                .doOnError(error -> System.out.println("Error searching questions: " + error));
    }

    @GetMapping("/cursor")
    //cursor aka ptr pointing to entity. In our case, we point to the questions created at timestamps
    public Flux<QuestionResponseDto> searchQuestionsByCursor(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.searchQuestionByCursor(cursor,size)
                .doOnComplete(() -> System.out.println("Search completed successfully with cursor: " + cursor))
                .doOnError(error -> System.out.println("Error searching questions: " + error));
    }

    @GetMapping("/tag/{tagId}")
    public Flux<QuestionResponseDto> searchQuestionsByTagId(
            @PathVariable String tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.getQuestionsByTagId(tagId,page,size)
                .doOnNext(response -> System.out.println("Questions retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error searching questions: " + error))
                .doOnComplete(() -> System.out.println("Search completed successfully"));
    }

    @GetMapping("/tag/any")
    public Flux<QuestionResponseDto> searchQuestionsByAnyTags(
            @RequestParam List<String> tagIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.getQuestionsByAnyTags(tagIds,page,size)
                .doOnNext(response -> System.out.println("Questions retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error searching questions: " + error))
                .doOnComplete(() -> System.out.println("Search completed successfully"));
    }

    @GetMapping("/tag/all")
    public Flux<QuestionResponseDto> searchQuestionsByAllTags(
            @RequestParam List<String> tagIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.getQuestionsByAllTags(tagIds,page,size)
                .doOnNext(response -> System.out.println("Questions retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error searching questions: " + error))
                .doOnComplete(() -> System.out.println("Search completed successfully"));
    }

    @GetMapping("/elasticsearch")
    public List<QuestionElasticDocument> searchQuestionByElasticSearch(
            @RequestParam String query
    ){
        return questionService.searchQuestionByElasticSearch(query);
    }

}