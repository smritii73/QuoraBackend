package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Question;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question,String> {

    @Query("{ $or :  [ { title : { $regex: ?0 , $options:  'i'} } , { content :  { $regex: ?0 , $options:  'i'} }]}")
    //regex compares 0th positioned argument
    Flux<Question> findByTitleOrContentContainingIgnoreCase(String searchTerm, Pageable pageable);
    // we need the pass the regex as the search term which is the 0th positonal parameter.
    Flux<Question> findByCreatedAtGreaterThanOrderByCreatedAtAsc(LocalDateTime cursor, Pageable pageable);
    Flux<Question> findTop10ByOrderByCreatedAtAsc(Pageable pageable); // just return the top 10 records

    // find question by single tagId
    @Query("{ 'tagIds' :  ?0}")
    Flux<Question> findByTagId(String tagId, Pageable pageable);

    // find questions by multiple tagIds (Questions that have ANY of these tags)
    @Query("{'tagIds' :  {$in : ?0}}")
    Flux<Question> findByTagIdIn(List<String> tagIds, Pageable pageable);

    // find questions by multiple tagIds (Questions that have ALL of these tags)
    @Query("{'tagIds' : {$all : ?0}}")
    Flux<Question> findByTagIdAll(List<String> tagIds, Pageable pageable);
}