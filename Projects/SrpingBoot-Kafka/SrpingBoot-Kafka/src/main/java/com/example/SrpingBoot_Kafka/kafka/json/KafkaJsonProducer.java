package com.example.SrpingBoot_Kafka.kafka.json;


import com.example.SrpingBoot_Kafka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonProducer {
    @Value("${kafka.topic.name_json}")
    private String jsonTopic;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void send(User user) {


        Message<User> message = MessageBuilder.withPayload(user).setHeader(KafkaHeaders.TOPIC, jsonTopic).build();
        kafkaTemplate.send(message);

    }
}
