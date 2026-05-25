package com.example.schema_registery_avro.Controller;


import com.example.schema_registery_avro.dto.Employee;
import com.example.schema_registery_avro.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class ApiController {


    @Autowired
    private EventProducer producer ;

    @PostMapping("/sendObject")

    public ResponseEntity sendContrObject(@RequestBody Employee employee)
    {
        producer.sendEvent(employee);
//        for ( int i=0 ;i<10;i++) { ///  when i send to many message the kafka will distribute it to many partion
//
//        }
        return  ResponseEntity.status(HttpStatus.OK).body(" message sent") ;

    }
}
