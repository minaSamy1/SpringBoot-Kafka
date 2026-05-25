package com.example.kafka_consumer.Controller;


import com.example.kafka_consumer.services.KafkaMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class ConsumerController {


    @Autowired
    private KafkaMessageListener kafkaMessageListener ;

//
//    @GetMapping("/consume")
//    public void Readmessage(){
//
//        kafkaMessageListener.getMessage();
//    }
}
