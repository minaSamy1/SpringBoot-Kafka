package com.example.producer;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${kafka.topic.name}")
    private String topicName ;


    public NewTopic topic()
    {
        /// creating new topic in broker
        return TopicBuilder.name(topicName).build() ;


    }
}
