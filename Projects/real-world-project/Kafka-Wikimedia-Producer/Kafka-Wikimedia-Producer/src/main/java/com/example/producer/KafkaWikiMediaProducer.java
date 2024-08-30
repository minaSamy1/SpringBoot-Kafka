package com.example.producer;


import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaWikiMediaProducer {


    public KafkaWikiMediaProducer(String topicName, KafkaTemplate<String, String> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public KafkaWikiMediaProducer() {
    }

    @Value("${kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void send() throws InterruptedException {


        EventHandler eventHandler = new WikimediaChangesHandler(topicName, kafkaTemplate);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(10);


    }
}
