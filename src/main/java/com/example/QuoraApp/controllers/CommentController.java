package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import com.example.QuoraApp.services.ICommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @PostMapping
    public Mono<CommentResponseDto> createComment(@RequestBody @Valid CommentRequestDto commentRequestDto) {
        return commentService.createComment(commentRequestDto)
                .doOnError(error -> System.out.println("Error creatin the comment : " + error.getMessage()))
                .doOnSuccess(comment -> System.out.println("Comment created successfully : " + comment));
    }

    @GetMapping("/{id}")
    public Mono<CommentResponseDto> getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id)
                .doOnSuccess(response -> System.out.println("Comment of " + id + " is retrieved sucessfully" + response))
                .doOnError(error -> System.out.println("Error retrieving comment of " + id + " " + error.getMessage()));

    }

    @GetMapping
    public Flux<CommentResponseDto> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return commentService.getAllComments(page, size)
                .doOnNext(response -> System.out.println("Comment retrieved: " + response))
                .doOnError(error -> System.out.println("Error while getting comment: " + error))
                .doOnComplete(() -> System.out.println("All comments fetched successfully"));
    }

}
