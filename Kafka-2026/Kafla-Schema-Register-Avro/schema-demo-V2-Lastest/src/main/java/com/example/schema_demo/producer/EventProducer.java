package com.example.schema_demo.producer;


import com.example.schema_demo.avro.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventProducer {

    @Autowired
    private KafkaTemplate<String,Object> template;




    public void Send(UserEvent event)
    {



        template.send("topic1", UUID.randomUUID().toString(),event);

    }
}
