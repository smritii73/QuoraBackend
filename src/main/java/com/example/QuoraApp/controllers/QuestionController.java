package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.QuoraApp.services.IQuestionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;

    @PostMapping
    public Mono<QuestionResponseDto> createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto){
        return questionService.createQuestion(questionRequestDto)
                .doOnSuccess(response->System.out.println("Question created successfully: " + response))
                .doOnError(error-> System.out.println("Error creating question : " + error));
    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDto> getQuestionById(@PathVariable String id){
        return questionService.getQuestionById(id)
                .doOnSuccess(response->System.out.println("Question retrieved successfully: " + response))
                .doOnError(error-> System.out.println("Error faced while getting the question : " + error));
    }

    @GetMapping
    public Flux<QuestionResponseDto> getAllQuestions(){
        return questionService.getAllQuestions()
                .doOnNext(response->System.out.println("Question retrieved successfully: " + response))
                .doOnComplete(()-> System.out.println("All questions retrieved successfully"))
                .doOnError(error->System.out.println("Error faced while getting the questions : " + error));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id){
       return questionService.deleteQuestionById(id)
               .doOnSuccess(response->System.out.println("Question deleted successfully: " + response))
               .doOnError(error->System.out.println("Error faced while deleting question by id: "+ error));
    }
}