package com.example.QuoraApp.adapter;


import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import com.example.QuoraApp.models.Comment;

import java.time.LocalDateTime;

public class CommentAdapter {

    public static CommentResponseDto toDto(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .targetId(comment.getTargetId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static  Comment toEntity(CommentRequestDto commentRequestDto){
        return Comment.builder()
                .text(commentRequestDto.getText())
                .targetId(commentRequestDto.getTargetId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
