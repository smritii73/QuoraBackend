package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.QuestionAdapter;
import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestDto questionRequestDto) {
        Question question = Question.builder()
                .title(questionRequestDto.getTitle()) // getTitle is the getter which sets the value in the setter .title , we take values from Dto and make Question.builder mei question
                .content(questionRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build(); //kyuki builder use krenge
        return questionRepository.save(question)
                .map(QuestionAdapter::toDto)
                .doOnSuccess(response->System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error));
    }

    @Override
    public Mono<QuestionResponseDto> getQuestionById(String id){
        Mono<Question> findQuestion = questionRepository.findById(id);
        return findQuestion.map(QuestionAdapter::toDto)
                .doOnSuccess(response->System.out.println("Question found:" + response))
                .doOnError(error -> System.out.println("Error getting question by id: " + error));
    }

    @Override
    public Flux<QuestionResponseDto> getAllQuestions(){
      Flux<Question> findAllQuestions = questionRepository.findAll();
      return findAllQuestions.map(QuestionAdapter::toDto)
              .doOnNext(response->System.out.println("Question found:" + response))
              .doOnComplete(()-> System.out.println("All questions retrieved successfully"))
              .doOnError(error -> System.out.println("Error getting all questions: " + error));
    }

    @Override
    public Mono<Void> deleteQuestionById(String id){
        Mono<Void> deleteQuestionById = questionRepository.deleteById(id);
        return deleteQuestionById
                .doOnSuccess(response-> System.out.println("Deleted successfully: " + response))
                .doOnError(error -> System.out.println("Error faced while deleting question by id: " + error));
    }

    @Override
    public Flux<QuestionResponseDto> searchQuestion(String searchTerm, Integer offset, Integer pageSize){
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset,pageSize))
                .map(QuestionAdapter::toDto)
                .doOnComplete(()-> System.out.println("All questions retrieved successfully"))
                .doOnError(error->System.out.println("Error getting questions: " + error));
    }
}