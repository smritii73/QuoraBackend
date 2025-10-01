package com.example.QuoraApp.producers;

import com.example.QuoraApp.events.ViewCountEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.QuoraApp.config.KafkaConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    /*The QuestionService will called KafkaEventProducer which called the KafkaTemplate and it tried to create the producer */
    public void publishViewCountEvent(ViewCountEvent viewCountEvent) {
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME,viewCountEvent.getTargetId(),viewCountEvent)
                .whenComplete((result,err)->{
                    if(err!=null) System.out.println("Error publishing view count event : " + err.getMessage());
                });
    }
}
