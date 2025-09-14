package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.TagAdapter;
import com.example.QuoraApp.dto.TagRequestDto;
import com.example.QuoraApp.dto.TagResponseDto;
import com.example.QuoraApp.models.Tag;
import com.example.QuoraApp.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public Mono<TagResponseDto> createTag(TagRequestDto tagRequestDto){
        Tag tag = TagAdapter.toEntity(tagRequestDto);
        return tagRepository.save(tag)
                .map(TagAdapter::toDto)
                .doOnSuccess(response-> System.out.println("Tag created sucessfully : " + response))
                .doOnError(error -> System.out.println("Error faced : " + error));
    }

    @Override
    public Mono<TagResponseDto> getTagById(String id){
        return tagRepository.findById(id)
                .map(TagAdapter::toDto)
                .doOnSuccess(response-> System.out.println("Tag get successfully : " + response))
                .doOnError(error -> System.out.println("Error faced : " + error));
    }

}
