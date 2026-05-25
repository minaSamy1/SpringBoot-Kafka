package com.example.kafka_error_handling.service;


import com.example.kafka_error_handling.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class Producer {


    @Autowired
    private KafkaTemplate<String , Object>template ;
    @Value("${topic.name}")
    private String topicName;

    public void send(User user)
    {


        CompletableFuture<SendResult<String, Object>> future =template.send(topicName ,user);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Message Sent Successfully: " + result.getRecordMetadata().offset());
            } else {
                System.err.println("Error while Sending Message: " + ex.getMessage());
            }

    });

    }


}
