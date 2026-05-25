package com.example.kafka_error_handling.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    @Value("${topic.name}")
    private String topicName;


    @Bean
    public NewTopic create() {

        return new NewTopic(topicName, 3, (short) (1));
    }
}
