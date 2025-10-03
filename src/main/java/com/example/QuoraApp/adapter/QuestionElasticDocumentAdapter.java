package com.example.QuoraApp.adapter;

import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;

public class QuestionElasticDocumentAdapter {
    public static QuestionElasticDocument toEntity(Question question){
        return QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();
    }

}
