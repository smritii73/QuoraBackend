package com.example.QuoraApp.adapter;


import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import com.example.QuoraApp.dto.UserResponseDto;
import com.example.QuoraApp.models.Comment;


public class CommentAdapter {

    public static CommentResponseDto toDto(Comment comment, UserResponseDto userResponseDto){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .targetId(comment.getTargetId())
                .targetType(comment.getTargetType())
                .createdByUser(userResponseDto)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static  Comment toEntity(CommentRequestDto commentRequestDto){
        return Comment.builder()
                .text(commentRequestDto.getText())
                .targetId(commentRequestDto.getTargetId())
                .targetType(commentRequestDto.getTargetType())
                .createdById(commentRequestDto.getCreatedById())
                .build();
    }
}
