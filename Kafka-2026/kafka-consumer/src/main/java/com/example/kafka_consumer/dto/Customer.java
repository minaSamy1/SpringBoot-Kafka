package com.example.kafka_consumer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {


    private String name;
    private String email;
    private String contactNo;
}
