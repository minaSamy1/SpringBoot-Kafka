package com.example.kafka_consumer.services;


import com.example.kafka_consumer.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);
    @KafkaListener(topics = "springtboot-topic2", groupId = "group1" , concurrency = "4")
    public void getMessage(String message) {
           log.info(" Consumer 1 receive "+message);

    }

    @KafkaListener(topics = "springtboot-topic-Object", groupId = "group2")
    public void getObject(Customer cust) {
        log.info(" Consumer Object "+cust.toString());

    }
//    @KafkaListener(topics = "springtboot-topic2", groupId = "group1")
//    public void getMessage2(String message) {
//        log.info(" Consumer 2 receive "+message);
//
//    }
//
//    @KafkaListener(topics = "springtboot-topic2", groupId = "group1")
//    public void getMessage3(String message) {
//        log.info(" Consumer 3 receive "+message);
//
//    }
//    @KafkaListener(topics = "springtboot-topic2", groupId = "group1")
//    public void getMessage4(String message) {
//        log.info(" Consumer 4 receive "+message);
//
//    }
//



//    @KafkaListener(topics = "springtboot-topic2", groupId = "group1")
//    public void getMessage1(String message) {
//        log.info(" Consumer 2 receive "+message);
//
//    }
}
