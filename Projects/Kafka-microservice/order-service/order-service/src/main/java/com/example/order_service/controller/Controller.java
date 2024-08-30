package com.example.order_service.controller;

import com.example.base_domain.dto.Order;
import com.example.base_domain.dto.OrderEvent;
import com.example.order_service.producer.MessageProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {


    @Autowired
private MessageProducer producer;


    @PostMapping("/order")
    public String sending(@RequestBody Order order)
    {

        OrderEvent event= new OrderEvent();
        event.setStatus("Pending");
        event.setMessage("Created");
        event.setOrder(order);

        producer.send(event);//  pushing the message to the topic

        return " Order send successfully";


    }
}
