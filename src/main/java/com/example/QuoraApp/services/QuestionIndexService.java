package com.example.QuoraApp.services;

import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.repositories.QuestionDocumentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService {

    private final QuestionDocumentRepository questionDocumentRepository;

    @Override
    public void createQuestionIndex(Question question){
        try {
            QuestionElasticDocument questionElasticDocument = QuestionElasticDocument.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .build();
            questionDocumentRepository.save(questionElasticDocument);
            System.out.println("Successfully indexed question : " + question.getId());
        }
        catch(Exception e){
            System.err.println("Failed to index the question : " + question.getId() + " : " + e.getMessage());
        }
    }
}
