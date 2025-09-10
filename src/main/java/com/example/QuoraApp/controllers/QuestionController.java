package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.QuoraApp.services.IQuestionService;
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


}