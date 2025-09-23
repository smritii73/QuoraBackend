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

/* What map does:

Takes the value inside the Mono
Applies your function to that value
Wraps the result back in a Mono

What flatMap does:

Takes the value inside the Mono
Applies your function to that value
Assumes your function returns a Mono and flattens it */

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
                .switchIfEmpty(Mono.error(new RuntimeException("Tag with id : " + id + " doesn't exists")))
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

    @Override
    public Mono<TagResponseDto> incrementUsageCount(String id){
        return tagRepository.findById(id) // this will return us Mono<Tag> as its reactive programming
        // we have to set the usage count of tag by usageCount + 1
        .flatMap(tag->{
            tag.setUsageCount(tag.getUsageCount()+1);
            return tagRepository.save(tag); //this will return Mono<Tag>
            // Mono<Tag> > Tag > Mono<Tag> > Mono<Mono<Tag>>
            // We will get this if we use Map. Now we flatten it to remove outer Mono, thus using flatMap
            // Now we have Mono<Tag> as outer Mono got removed.
        }).map(TagAdapter::toDto);
    }
    /*
    we have an id, firstly get the tag from the repository using the id.
    Now we get the Tag using findByName(id). This will return a Mono<Tag>. This is the output.
    Now we have to do operations on the output. We will take out the Tag from the output Mono<Tag>.
    Now we have Tag which we have taken out from Mono<Tag>.
    Now we will set the value of usageCount - 1.
    We have to check if the usageCount isnt less than 0.
    Now we have updated the Tag variable but we need to update the db also so we will do repository.save(updatedTag)
    .save returns Mono<Tag>.
    So, our Tag viz taken out from the output has now got converted to Mono<Tag>.
    Now we have to put it again in Mono from which we have taken out earlier
    Finally we get, Mono<Mono<Tag>>.
    We dont want the outer Mono so we will flatten it usign flatMap.
    Thus finally we have Mono<Tag> only but our func needs Mono<TagResponseDto>.
    So we use .map and TagAdapter to convert to Dto.
     */

    @Override
    public Mono<TagResponseDto> decrementUsageCount(String id){
        return tagRepository.findById(id)// Mono<Tag>
        .flatMap(tag-> { // Tag object nikala gya hai!
           tag.setUsageCount(Math.max(0,(tag.getUsageCount() - 1))); // apne funct ke acc dhaal chuke
           return tagRepository.save(tag); // Mono<Mono<Tag>>
        }).map(TagAdapter::toDto);
    }

    @Override
    public Mono<TagResponseDto> findTagByName(String name){
        return tagRepository.findByName(name)
                .map(TagAdapter::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Tag with name : " + name + " doesn't exists")))
                .doOnSuccess(response-> System.out.println("Tag get successfully : " + response))
                .doOnError(error -> System.out.println("Error faced : " + error));
    }

}