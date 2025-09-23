package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.QuestionAdapter;
import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.repositories.QuestionRepository;
import com.example.QuoraApp.utils.CursorUtils;
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
        Question question = QuestionAdapter.toEntity(questionRequestDto);
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
      return findAllQuestions
              .map(QuestionAdapter::toDto)
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
    public Flux<QuestionResponseDto> searchQuestions(String searchTerm, Integer offset, Integer pageSize) {
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset,pageSize))
                .map(QuestionAdapter::toDto)
                .doOnError(error -> System.out.println("Error getting questions: " + error))
                .doOnComplete(() -> System.out.println("All questions retrieved successfully"));
    }

    @Override
    public Flux<QuestionResponseDto> searchQuestionByCursor(String cursor, int size){
        /*what we should check is if we again want a certain no. records then we shuld use the pageable obj
        but we dont want the offset then we keep the offset as zero
        If we want the size, we can use the pageable object
        Now we have to check if we passed the cursor to us or not, as in the controller it was(required=false)
        To check we have the cursor or not, we have the cursor util
        Now the most imp thing is , that the ordering should be done using createdAt
         */
        Pageable pageable = PageRequest.of(0,size);
        if(!CursorUtils.isValidCursor(cursor)){
            //here we arent getting the cursor so we have to return the top 10 records
            return questionRepository.findTop10ByOrderByCreatedAtAsc(pageable)
                    .map(QuestionAdapter::toDto)
                    .doOnComplete(() -> System.out.println("All questions retrieved successfully"))
                    .doOnError(error -> System.out.println("Error getting questions: " + error));
        }
        else{
            // here we got the cursor and will return the records wrt the timestamp provided in the cursor
            // the cursor has the timestamp
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toDto)
                    .doOnComplete(() -> System.out.println("All questions retrieved successfully"))
                    .doOnError(error -> System.out.println("Error getting questions: " + error));
        }
    }
}