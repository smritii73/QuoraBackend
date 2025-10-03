package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.QuestionElasticDocumentAdapter;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.repositories.QuestionDocumentRepository;
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
                    .then(); // completes the Mono without emitting the saved document
    }
}
