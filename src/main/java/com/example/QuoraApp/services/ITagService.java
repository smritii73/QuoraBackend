package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.TagRequestDto;
import com.example.QuoraApp.dto.TagResponseDto;
import lombok.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITagService {
    public Mono<TagResponseDto> createTag(TagRequestDto tagRequestDto);
    public Mono<TagResponseDto> getTagById(String id);
    public Flux<TagResponseDto> getAllTags(int page, int size);
    public Mono<TagResponseDto> incrementUsageCount(String id);
    public Mono<TagResponseDto> decrementUsageCount(String id);
    public Mono<TagResponseDto> findTagByName(String name);
}