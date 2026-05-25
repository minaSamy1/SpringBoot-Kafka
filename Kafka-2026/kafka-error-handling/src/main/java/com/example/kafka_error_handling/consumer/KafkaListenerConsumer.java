package com.example.kafka_error_handling.consumer;


import com.example.kafka_error_handling.dto.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class KafkaListenerConsumer {

    private String topicName;

    @RetryableTopic(attempts = "4")// 3 topic N-1
    @KafkaListener(topics = "handler-demo", groupId = "group1")
    public void readMessage(User user) {


        System.out.println(" recived object " + user.toString());
        List<String> e = Arrays.asList("192.168.111.1", "192.168.111.12");
        for (String current : e) {


            if (current.contains(user.getIpAddress())) {
                throw new RuntimeException("Invalid IP Address received !");
            }

        }

    }


    //
    @DltHandler
    public void listenDLT(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("DLT Received : {} , from {} , offset {}", user.getFirstName(), topic, offset);
    }
}




