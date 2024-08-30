package com.example.Kafka_Wikimedia_Consumer;

import com.example.Kafka_Wikimedia_Consumer.entity.WikimediaData;
import com.example.Kafka_Wikimedia_Consumer.repository.Repo;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {


    @Value("${kafka.topic.name}")
    private String topic;
@Autowired
private Repo repo;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void getmessage(String message)
    {


        System.out.println(" Read Message from Topic "+message);
        WikimediaData data = new WikimediaData();
        data.setWikiEventData(message);
        repo.save(data);


    }
}

