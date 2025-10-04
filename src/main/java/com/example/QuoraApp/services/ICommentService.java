package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICommentService {
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto);
    public CommentResponseDto getAllComments();
    public CommentResponseDto getCommentById(String id);
}
