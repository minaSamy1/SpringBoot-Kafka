package com.example.producer;


import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaChangesHandler implements EventHandler {
    @Value("${kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesHandler(String topicName, KafkaTemplate<String, String> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        System.out.println(" On Message before push message .....> "+ messageEvent.getData());

        kafkaTemplate.send(topicName, messageEvent.getData());
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
