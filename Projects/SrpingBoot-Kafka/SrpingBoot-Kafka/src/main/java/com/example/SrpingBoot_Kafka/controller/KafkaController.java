package com.example.SrpingBoot_Kafka.controller;

import com.example.SrpingBoot_Kafka.kafka.KafkaProducer;
import com.example.SrpingBoot_Kafka.kafka.json.KafkaJsonProducer;
import com.example.SrpingBoot_Kafka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaJsonProducer kafkaJsonProducer;
    @GetMapping("/send")
    public String sendApi(@RequestParam String message)
    {

        kafkaProducer.sendMessage(message);
        return "Send Message to Kafka server ";
        /// after call the api you can check if the mesage push to kafka server by
        // C:\kafka>.\bin\windows\kafka-console-consumer.bat --topic topic1 --from-beginning  --bootstrap-server localhost:9092
    }

    @PostMapping("/sendJson")
    public String sendApi(@RequestBody User user)
    {

        kafkaJsonProducer.send(user);
        return "Send Message to Kafka server ";
        /// after call the api you can check if the mesage push to kafka server by
        // C:\kafka>.\bin\windows\kafka-console-consumer.bat --topic topic1 --from-beginning  --bootstrap-server localhost:9092
    }
}
