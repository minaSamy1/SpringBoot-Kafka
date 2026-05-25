package com.example.kafka_error_handling.controller;


import com.example.kafka_error_handling.dto.User;
import com.example.kafka_error_handling.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/kafka")
public class ProducerController {
@Autowired
    private Producer producer ;


    @PostMapping()
    public ResponseEntity<?> sendMessage(@RequestBody User user)
    {


        producer.send(user);
        return  new ResponseEntity(" Message sent ",HttpStatus.CREATED);
    }



}
