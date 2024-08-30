package com.example.order_service.producer;


import com.example.base_domain.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Value("${kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void send(OrderEvent event) {


        System.out.println(" Sending Message " + event.toString());
        Message<OrderEvent> message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC, topicName).build();
        kafkaTemplate.send(message);

    }
}
