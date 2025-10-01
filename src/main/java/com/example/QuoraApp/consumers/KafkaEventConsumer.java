package com.example.QuoraApp.consumers;

import com.example.QuoraApp.config.KafkaConfig;
import com.example.QuoraApp.events.ViewCountEvent;
import com.example.QuoraApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    @KafkaListener(
        topics = KafkaConfig.TOPIC_NAME,
        groupId = "view-count-consumer",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
        /*if(viewCountEvent.getTargetType().equals("question")) {
        } else if(viewCountEvent.getTargetType().equals("answer")) {
        } else if(viewCountEvent.getTargetType().equals("comment")) {} */
        questionRepository.findById(viewCountEvent.getTargetId()) //gives corresponding Q
        // but findById is going to give us the Mono of the question
        /*suppose we have the Q and we want to inc the Q viewcount and return the responseof QuestionRepository.save
       * so QuestionRepository is going to return me another Mono so to avoid that we should do a flatMap here instead of map*/
        .flatMap(question->{
            System.out.println("Incrementing the view count for the consumer: " + question.getId());
            Integer views = question.getViews();
            question.setViews(views == null ? 0 : views + 1);
            return questionRepository.save(question);
        }) //whatever this flatMap returns, we can subscribe on that asyncronously
                .subscribe(updatedQuestion->{
                    System.out.println("View count incremented for question : " + updatedQuestion.getId());
                },error->{
                    System.out.println("Error occuring view count for question : " + error.getMessage());
                });
    }
}
/* we will be addign a ViewCountIncrementStrategy interface then have implementation for it
QuestionViewCountIncrementStrategy and AnswerViewCountIncrementStrategy
then we will make ViewCountStrategyFactory > Factory class responsible for creating instances of the strategy classes
and our consume class will depend on one of the instances marked by the interface so that you can depend on the
polymorphic type
here, just create the instance of the corresponding strategy and call the same function

generally factory class comprises of all the new instance creation we have to handle. its responsible to create objects
you donot want new obj creation lying here and there around the code base because if any thing changes in the object creation,
you have to change it everywhere but if yu have consolidated everything in a factory class, you are following the SRP
& that class is only repsonsible for creating objects of a certain type and if obj creation logic changes,
you just have to change it once
*  */