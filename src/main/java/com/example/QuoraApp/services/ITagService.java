package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.TagRequestDto;
import com.example.QuoraApp.dto.TagResponseDto;
import lombok.*;
import reactor.core.publisher.Mono;

public interface ITagService {
    public Mono<TagResponseDto> createTag(TagRequestDto tagRequestDto);
    public Mono<TagResponseDto> getTagById(String id);
}
