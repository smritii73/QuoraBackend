package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.CommentAdapter;
import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import com.example.QuoraApp.models.Comment;
import com.example.QuoraApp.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService{

    private final CommentRepository commentRepository;

    @Override
    public Mono<CommentResponseDto> createComment(CommentRequestDto commentRequestDto){
        Comment comment = CommentAdapter.toEntity(commentRequestDto);
        return commentRepository.save(comment)
                .map(CommentAdapter::toDto)
                .doOnSuccess(response -> System.out.println("The Comment has been created successfully : " + response))
                .doOnError(error -> System.out.println("Comment created failed : " + error.getMessage()));
    }

    @Override
    public Mono<CommentResponseDto> getCommentById(String id){
        return commentRepository.findById(id)
                .map(CommentAdapter::toDto)
                .doOnSuccess(response -> System.out.println("The comment has been get successfully : " + response))
                .doOnError(error -> System.out.println("Comment get failed : " + error.getMessage()));
    }

    @Override
    public Flux<CommentResponseDto> getAllComments(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return commentRepository.findAllBy(pageable)
                .map(CommentAdapter::toDto)
                .doOnNext(response -> System.out.println("The comment has been get successfully : " + response))
                .doOnError(error -> System.out.println("Comment get failed : " + error.getMessage()))
                .doOnComplete(() -> System.out.println("The comment has been get successfully"));
    }
}
