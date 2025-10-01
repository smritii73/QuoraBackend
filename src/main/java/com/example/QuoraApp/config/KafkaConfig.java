package com.example.QuoraApp.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id:my-group}")
    private String groupId;

    public static final String TOPIC_NAME = "view-count-topic";

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        // this function is going to create a producer factory to create a producer
        // factories refer to a class that have the responsibility of creation of some object
        /* one might wonder we can call the constructor with a new operator but thats the naive way of doing it
        * If we do new new in all our services it will simply pollute the codebase
        * Instead what we have done is we have a configuration class where we have a @Bean which will automatically
        * give us the ProducerFactory whenever we need it but think wrt Kafka's library implementation.
        * Internally kafka will also perform operations to create this producer.
        * So whenever we have a class whose objects can be created at lot of places, 
        * its a good practice to make a factory class, inside that factory class we have a method which in some way
        * gets the configuration to create the object and creates the object for you to avoid new-new everywhere
        * To know more about it > read the factory design pattern */
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    // this was for producer factory, now create for consumer factory
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        /*when Kafka tries to de serialize,it will only try to deserialize from a trusted package which can bedone easily
         * via a consumerfactory by marking a package trusted*/
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.QuoraApp.events");
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    // these are going to be the two factory implementations that we will do
    /* there will be more @Bean creations -> 1st being KafkaTemplateObject
    because when we will create sendMessages in Kafka Template object
    */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        /* This will build your KafkaListenerMethods
        * and now we can decided things like what should be our ConsumerFactory(so we have SetConsumerFactory)
        * or set what is the concurrency, how many messages should be processed concurrently*/
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3); // at any point of time, allow 3 msgs to be read concurrently
        // we can also define a corresponding title name above
        return factory;
    }
    /* Technically we are ready with the setup of kafka
    * we need to make sure that whenever we have a view on the question , at that time we should be able to publish the event*/
}
