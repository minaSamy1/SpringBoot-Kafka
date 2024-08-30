package com.example.SrpingBoot_Kafka.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String messageTopic;
    public void sendMessage(String message) {

        kafkaTemplate.send(messageTopic, message);


    }
}



