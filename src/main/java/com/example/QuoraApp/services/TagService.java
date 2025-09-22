package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.TagAdapter;
import com.example.QuoraApp.dto.TagRequestDto;
import com.example.QuoraApp.dto.TagResponseDto;
import com.example.QuoraApp.models.Tag;
import com.example.QuoraApp.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public Mono<TagResponseDto> createTag(TagRequestDto tagRequestDto){
        // The flow is we give name and description and it saves it as a tag
        return tagRepository.findByName(tagRequestDto.getName()) // findByName returns Mono<Tag>
                .flatMap(tag->
                        // {} if curly braces there> return statement reqd else, not reqd
                    Mono.<TagResponseDto>error(new RuntimeException(
                            "Tag with name " + tagRequestDto.getName() + " already exists"
                    ))
                    // we got Mono<Tag> from findByName
                    // then we took out tag from Mono and converted it to Mono<TagResponseDto>
                    // now whatever we get, we put back in Mono
                    // according to this, we got Mono<Mono<TagResponseDto>>
                    // we do not want Mono in Mono
                    // thus, we use flatmap which will remove the outer Mono and we get Mono<TagResponseDto>
                )
                .switchIfEmpty(
                        // the Mono has come empty, ie no same name tag is present in the db
                        // so now we create 1 and same in db
                        tagRepository
                                .save(TagAdapter.toEntity(tagRequestDto))
                                .map(TagAdapter::toDto)
                )
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

    @Override
    public Flux<TagResponseDto> getAllTags(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return tagRepository.findAllBy(pageable)
                .map(TagAdapter::toDto)
                .doOnNext(response-> System.out.println("Tag get successfully : " + response))
                .doOnError(error -> System.out.println("Error faced : " + error))
                .doOnComplete(()-> System.out.println("Tag get successfully"));
    }

}