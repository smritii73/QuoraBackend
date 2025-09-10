package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.QuestionAdapter;
import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestDto questionRequestDto) {
        Question question = Question.builder()
                .title(questionRequestDto.getTitle()) // getTitle is the getter which sets the value in the setter .title , we take values from Dto and make Question.builder mei question
                .content(questionRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build(); //kyuki builder use krenge
        return questionRepository.save(question)
                .map(QuestionAdapter::toDto)
                .doOnSuccess(response->System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error));
    }
}