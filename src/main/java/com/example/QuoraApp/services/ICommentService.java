package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICommentService {
    Mono<CommentResponseDto> createComment(CommentRequestDto commentRequestDto);
    Mono<CommentResponseDto> getCommentById(String id);
    Flux<CommentResponseDto> getAllComments(int page, int size);
}
