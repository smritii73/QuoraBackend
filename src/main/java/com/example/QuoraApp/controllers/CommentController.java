package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import com.example.QuoraApp.models.Comment;
import com.example.QuoraApp.services.ICommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @PostMapping
    public void createComment(@RequestBody @Valid CommentRequestDto commentRequestDto) {
        return commentService.createComment(commentRequestDto);
    }

    @GetMapping("/{id}")
    public CommentResponseDto getCommentById(@PathVariable String id) {
        return this.commentService.getCommentById(id);
    }

    @GetMapping
    public CommentResponseDto getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return commentService.getAllComments(page, size)
                .doOnNext(response-> System.out.println("Comment retrieved successfully: " + response))
                .doOnError(error-> System.out.println("Error while getting comment: " + error))
                .doOnComplete(()-> System.out.println("Comment got successfully"));
    }

}
