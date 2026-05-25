package com.example.schema_demo.consumer;


import com.example.schema_demo.avro.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {


    @KafkaListener(topics = "topic1")
    public void read(ConsumerRecord<String, UserEvent> consumerRecord) {
        String key = consumerRecord.key();
        UserEvent userEvent = consumerRecord.value();
        System.out.println("Avro message received for key : " + key + " value : " + userEvent.toString());

    }
}
