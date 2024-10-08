package com.example.order_service.configuration;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${kafka.topic.name}")
    private String topicName;


    public NewTopic topic()
    {


        return TopicBuilder.name(topicName).build();
    }
}
