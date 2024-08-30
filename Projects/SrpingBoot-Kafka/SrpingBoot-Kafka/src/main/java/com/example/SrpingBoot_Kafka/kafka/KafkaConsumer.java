package com.example.SrpingBoot_Kafka.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    @KafkaListener(topics = "${kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void getMessage(String message)
    {
        System.err.println(" Reading Message From our Topic "+ message);
    }
}
