package com.example.kafka.controller;

import com.example.kafka.dto.Customer;
import com.example.kafka.service.ProducerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class producerController {
    @Autowired
 private   ProducerServices producerServices ;

    @GetMapping("/send")

    public ResponseEntity sendContr(@RequestParam String message)
    {
            for ( int i=0 ;i<10000;i++) { ///  when i send to many message the kafka will distribute it to many partion
                producerServices.sendEvent(message+" "+i);
            }
        return  ResponseEntity.status(HttpStatus.OK).body(" message sent") ;

    }

    @GetMapping("/sendObject")

    public ResponseEntity sendContrObject(@RequestBody Customer customer)
    {
        for ( int i=0 ;i<10;i++) { ///  when i send to many message the kafka will distribute it to many partion
            producerServices.sendObject(customer);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(" message sent") ;

    }
}
