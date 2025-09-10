package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.models.Question;

public class QuestionAdapter {
    public static QuestionResponseDto toDto(Question question){  //it is a response dto
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .build();
    }
}