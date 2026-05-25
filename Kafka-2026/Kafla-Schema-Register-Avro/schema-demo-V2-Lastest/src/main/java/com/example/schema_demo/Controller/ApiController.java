package com.example.schema_demo.Controller;


import com.example.schema_demo.avro.UserEvent;
import com.example.schema_demo.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka-schema")
public class ApiController {

    @Autowired
    private EventProducer producer;

    @PostMapping("/sendObject")

    public ResponseEntity sendContrObject(@RequestBody UserEvent event) {
        producer.Send(event);

        return ResponseEntity.status(HttpStatus.OK).body(" message sent");

    }

    @GetMapping("/test")

    public ResponseEntity sendContrOdbject() {

        return ResponseEntity.status(HttpStatus.OK).body(" message sent");

    }

}
