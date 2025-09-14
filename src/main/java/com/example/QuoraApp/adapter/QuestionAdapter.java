package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.dto.TagResponseDto;
import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.models.Question;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionAdapter {
    public static QuestionResponseDto toDto(Question question){  //it is a response dto
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .tagIds(question.getTagIds())
                .createdAt(question.getCreatedAt())
                .build();
    }

    public static Question toEntity(QuestionRequestDto questionRequestDto){
        return Question.builder()
                .title(questionRequestDto.getTitle())
                // getTitle is the getter which sets the value in the setter .title ,
                // we take values from Dto and make Question.builder mei question
                .content(questionRequestDto.getContent())
                .tagIds(questionRequestDto.getTagIds())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build(); //kyuki builder use krenge
    }

    public static QuestionResponseDto toDtoWithTags(Question question, List<TagResponseDto> tags){
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .tagIds(question.getTagIds())
                .tags(tags)
                .createdAt(question.getCreatedAt())
                .build();
    }
}