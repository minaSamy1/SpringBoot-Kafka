package com.example.SrpingBoot_Kafka.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.name}")
   private String messageTopic;
    @Value("${kafka.topic.name_json}")
    private String messageJsonTopic;
    public NewTopic messageTopic()
    {

        return TopicBuilder.name(messageTopic).build();

    }


    public NewTopic messageJosnTopic()
    {

        return TopicBuilder.name(messageJsonTopic).build();

    }
}
