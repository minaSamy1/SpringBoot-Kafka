package com.example.schema_registery_avro.Consumer;


import com.example.schema_registery_avro.dto.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

public class EventConsumer {



    @KafkaListener(topics = "${topic.name}")
    public void read(ConsumerRecord<String, Employee> consumerRecord) {
        String key = consumerRecord.key();
        Employee employee = consumerRecord.value();
        System.out.println("Avro message received for key : " + key + " value : " + employee.toString());

    }
}
