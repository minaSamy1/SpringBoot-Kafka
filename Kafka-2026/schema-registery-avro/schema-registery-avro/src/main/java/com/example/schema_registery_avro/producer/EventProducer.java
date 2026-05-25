package com.example.schema_registery_avro.producer;


import com.example.schema_registery_avro.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class EventProducer {


    @Autowired
    private KafkaTemplate<String, Object> template ;




    public void sendEvent(Employee employee) {

       /// CompletableFuture<SendResult<String, Object>> future = template.send("springtboot-schema",message);
        CompletableFuture<SendResult<String, Object>> future = template.send("springtboot-schema", UUID.randomUUID().toString(),employee);
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


}
