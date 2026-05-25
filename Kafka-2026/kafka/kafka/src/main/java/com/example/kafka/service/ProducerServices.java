package com.example.kafka.service;


import com.example.kafka.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProducerServices {

    @Autowired
    private KafkaTemplate<String, Object> template;


    public void sendEvent(String message) {

        // incase the topic () not exist on kafka it will create it with 1 partion
          /// CompletableFuture<SendResult<String, Object>> future = template.send("springboot-topic",message);
        // incase you define Bean to create Topic it will looad that one
       // Kafka send to any partion
        CompletableFuture<SendResult<String, Object>> future = template.send("springtboot-topic2",message);
        ///  incase you need to send message to Speacfici partion
     ///   CompletableFuture<SendResult<String, Object>> future = template.send("springtboot-topic2",2,null,message);
        future.whenComplete((result, ex) ->
        {
            if (ex == null) {
                System.out.println(" Message Send Successfully" +
                        "");
            } else {

                System.out.println(" Error while Sending Message ");
            }
        });

    }


    public void sendObject(Customer message) {

        // incase the topic () not exist on kafka it will create it with 1 partion
        /// CompletableFuture<SendResult<String, Object>> future = template.send("springboot-topic",message);
        // incase you define Bean to create Topic it will looad that one
        // Kafka send to any partion
        CompletableFuture<SendResult<String, Object>> future = template.send("springtboot-topic-Object",message);
        ///  incase you need to send message to Speacfici partion
        ///   CompletableFuture<SendResult<String, Object>> future = template.send("springtboot-topic2",2,null,message);
        future.whenComplete((result, ex) ->
        {
            if (ex == null) {
                System.out.println(" object Send Successfully" +
                        "");
            } else {

                System.out.println(" Error while Sending Message ");
            }
        });

    }
}
