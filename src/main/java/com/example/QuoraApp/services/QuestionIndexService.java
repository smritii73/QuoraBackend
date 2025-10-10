package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.QuestionElasticDocumentAdapter;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.repositories.QuestionDocumentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService {

    private final QuestionDocumentRepository questionDocumentRepository;

    @Override
    public Mono<Void> createQuestionIndex(Question question){
            QuestionElasticDocument questionElasticDocument = QuestionElasticDocumentAdapter.toEntity(question);
            return questionDocumentRepository.save(questionElasticDocument)
                    .doOnSuccess(response-> System.out.println("Successfully indexed question: " + response))
                    .doOnError(error-> System.err.println("Failed to index the question : " + question.getId() + " " + error.getMessage()))
                    .then(); // completes the Mono without emitting the saved document response
    }

    public Flux<QuestionElasticDocument> searchQuestionByElasticSearch(String query){
        return questionDocumentRepository.findByTitleContainingOrContentContaining(query, query)
                .doOnNext(response-> System.out.println("Question found: " + response))
                .doOnComplete(() -> System.out.println("All questions retrieved successfully"))
                .doOnError(error -> System.out.println("Error getting questions: " + error));
    }

    public Mono<Void> deleteAllQuestions(){
        return questionDocumentRepository.deleteAll()
                .doOnSuccess(response-> System.out.println("All questions deleted successfully"))
                .doOnError(error-> System.err.println("Failed to delete all the questions : " + error.getMessage()));
    }

    public Mono<Void> deleteQuestionById(String id){
        return questionDocumentRepository.deleteById(id)
                .doOnSuccess(response-> System.out.println("Question deleted successfully"))
                .doOnError(error-> System.err.println("Failed to delete the question : " + id));
    }
}
