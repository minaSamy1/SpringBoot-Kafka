package com.example.kafka.configuration;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {


    @Bean
    public NewTopic createTopic()
    {
        return  new NewTopic("springtboot-topic2", 5, (short) (1));
    }

    @Bean
    public NewTopic createTopic2()
    {
        return  new NewTopic("springtboot-topic-Object", 5, (short) (1));
    }
}


