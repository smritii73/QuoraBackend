package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.QuestionElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface QuestionDocumentRepository extends ReactiveElasticsearchRepository<QuestionElasticDocument,String> {
    Flux<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title, String content);

}
