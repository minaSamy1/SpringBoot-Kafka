package com.example.SrpingBoot_Kafka.kafka.json;

import com.example.SrpingBoot_Kafka.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonConsumer {




    @KafkaListener(topics = "${kafka.topic.name_json}")
    public void getJsonMessage( User user)
    {

        System.out.println(" Read Json Message -> "+ user);


    }
}
