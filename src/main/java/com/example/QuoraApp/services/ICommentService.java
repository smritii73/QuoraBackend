package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICommentService {
    public Mono<CommentResponseDto> createComment(CommentRequestDto commentRequestDto);
    public Mono<CommentResponseDto> getCommentById(String id);
    public Flux<CommentResponseDto> getAllComments(int page, int size);
}
