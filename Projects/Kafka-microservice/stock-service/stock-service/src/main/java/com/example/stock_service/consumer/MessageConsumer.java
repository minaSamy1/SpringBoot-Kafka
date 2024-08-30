package com.example.stock_service.consumer;


import com.example.base_domain.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {


    @Value("${kafka.topic.name}")
    private String topicName;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public  void readMessage(
            OrderEvent event
    )
    {


        System.err.println(" Reading message  In Stock services "+event.toString());
    }
}
