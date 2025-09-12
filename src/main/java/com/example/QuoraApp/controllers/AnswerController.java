package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.AnswerResponseDto;
import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.services.AnswerService;
import com.example.QuoraApp.services.IAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final IAnswerService answerService;

    @PostMapping
    public Mono<AnswerResponseDto> createAnswer(@Valid @RequestBody AnswerRequestDto answerRequestDto) {
        return answerService.createAnswer(answerRequestDto)
                .doOnSuccess(response->System.out.println("Answer created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating answer: " + error));
    }

    @GetMapping("/{id}")
    public Mono<AnswerResponseDto> getAnswersById(@PathVariable String id){
        return answerService.getAnswersById(id)
                .doOnSuccess(response->System.out.println("Answer found successfully: " + response))
                .doOnError(error-> System.out.println("Error found at answer: " + error));
    }


}
