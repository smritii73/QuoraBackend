package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.CommentAdapter;
import com.example.QuoraApp.dto.CommentRequestDto;
import com.example.QuoraApp.dto.CommentResponseDto;
import com.example.QuoraApp.models.Comment;
import com.example.QuoraApp.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto){
        Comment comment = CommentAdapter.toEntity(commentRequestDto);
        return commentRepository.save(comment).map(CommentAdapter::toDto);
    }

    @Override
    public CommentResponseDto getCommentById(String id){
        return commentRepository.findById(id).map(CommentAdapter::toDto);
    }

    @Override
    public CommentResponseDto getAllComments(){
        return commentRepository.findAllBy().map(CommentAdapter::toDto);
    }
}
