package com.example.QuoraApp.controllers;

import com.example.QuoraApp.dto.TagRequestDto;
import com.example.QuoraApp.dto.TagResponseDto;
import com.example.QuoraApp.services.ITagService;
import com.example.QuoraApp.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    @PostMapping
    public Mono<TagResponseDto> createTag(@Valid @RequestBody TagRequestDto tagRequestDto){
        return tagService.createTag(tagRequestDto)
                .doOnSuccess(response-> System.out.println("Tag created successfully: " + response))
                .doOnError(error-> System.out.println("Error while creating tag: " + error));
    }

    @GetMapping("/{id}")
    public Mono<TagResponseDto> getTagById(@PathVariable String id){
       return tagService.getTagById(id)
                .doOnSuccess(response-> System.out.println("Tag get successfully: " + response))
                .doOnError(error-> System.out.println("Error while getting tag: " + error));
    }

    @GetMapping
    public Flux<TagResponseDto> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return tagService.getAllTags(page, size)
                .doOnNext(response-> System.out.println("Tag get successfully: " + response))
                .doOnError(error-> System.out.println("Error while getting tags: " + error))
                .doOnComplete(()-> System.out.println("Tag get successfully"));
    }

    @GetMapping("/name/{name}")
    public Mono<TagResponseDto> getTagByName(@PathVariable String name){
        return tagService.findTagByName(name)
                .doOnSuccess(response-> System.out.println("Tag get successfully: " + response))
                .doOnError(error-> System.out.println("Error while getting tag: " + error));
    }
}