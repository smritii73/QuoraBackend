package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.AnswerResponseDto;
import com.example.QuoraApp.models.Answer;

import java.time.LocalDateTime;

public class AnswerAdapter {

    public static AnswerResponseDto toDto(Answer answer){
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .questionId((answer.getQuestionId()))
                .createdAt(answer.getCreatedAt())
                .build();
    }

    public static Answer toEntity(AnswerRequestDto answerRequestDto){
        return Answer.builder()
                .content(answerRequestDto.getContent())
                .questionId(answerRequestDto.getQuestionId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}