

# KAFKA & SPRING BOOT: DEVELOPMENT REFERENCE GUIDE

## 1. ENVIRONMENT SETUP (DOCKER KRAFT MODE)

Architecture:** Using Kafka without Zookeeper (KRaft Mode).
Image:** `confluentinc/cp-kafka:7.6.0`
Mandatory Config:** A `CLUSTER_ID` must be provided in environment variables to enable the KRaft controller and prevent container exit.

### Testing the Container (CLI)

1.**Enter Container:**
      docker exec -it kafka /bin/bash`
2.**Create Topic (Manual):**
    kafka-topics --bootstrap-server localhost:9092 --create --topic my_topic --partitions 5 --replication-factor 1`
3.**List Topics:**
    kafka-topics --bootstrap-server localhost:9092 --list`
4.**Console Producer (Manual Test):**
    kafka-console-producer --bootstrap-server localhost:9092 --topic my_topic`
5.**Console Consumer (Manual Test):**
     kafka-console-consumer --bootstrap-server localhost:9092 --topic my_topic --from-beginning`

---

## 2. SPRING BOOT INTEGRATION

* **Library:** `spring-boot-starter-kafka` (Leverages Auto-configuration).
* **Dependencies:** Requires `jackson-databind` for Object (JSON) serialization.

### Step 1: Create Topic with Custom Properties (Bean)

java
@Bean
public NewTopic createTopic() {
    // Topic Name, Number of Partitions, Replication Factor
    return new NewTopic("springboot-topic-2", 5, (short) 1);
}

```

### Step 2: Configuration for String vs. Object
spring.kafka.bootstrap-servers=localhost:9092

**For String:**
`spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer`

**For Objects (JSON):**
`spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer`

---

## 3. PRODUCER IMPLEMENTATION (KAFKATEMPLATE) : what we did 

#Sending Messages

1.Basic Send:** `template.send(topic, message);`
2.Specific Partition:** `template.send(topic, partitionIndex, key, message);`
* *Note: Index is zero-based (e.g., 0-4 for 5 partitions).*




### Notes on Topic Creation and Message Sending in Kafka (Spring Boot)


// 1) If the topic does NOT exist in Kafka
// Kafka (or Spring Kafka) will automatically create it with 1 partition
CompletableFuture<SendResult<String, Object>> future =
        template.send("springboot-topic", message);


// 2) If you define a NewTopic @Bean in Spring Boot
// Spring will use the topic configuration you defined (partitions, replicas, etc.)
// Kafka will send the message to any partition based on the partitioner
CompletableFuture<SendResult<String, Object>> future =
        template.send("springboot-topic2", message);


// 3) If you want to send a message to a specific partition
// You explicitly pass: topic, partition number, key (optional), and value
CompletableFuture<SendResult<String, Object>> future =
        template.send("springboot-topic2", 2, null, message);






### Implementation Example (Asynchronous)




@Autowired
private KafkaTemplate<String, Object> template;

public void sendEvent(String message) {
    CompletableFuture<SendResult<String, Object>> future = 
        template.send("springboot-topic-2", message);

    future.whenComplete((result, ex) -> {
        if (ex == null) {
            System.out.println("Message Sent Successfully: " + result.getRecordMetadata().offset());
        } else {
            System.err.println("Error while Sending Message: " + ex.getMessage());
        }
    });
}

- Moving to sending Object based on Serlization configuration 

### Sending Objects (DTOs)

```java
public void sendObject(Customer customer) {
    // Ensure value-serializer is set to JsonSerializer
    CompletableFuture<SendResult<String, Object>> future = 
        template.send("springboot-topic-Object", customer);

    future.whenComplete((result, ex) -> {
        if (ex == null) {
            System.out.println("Object Sent Successfully");
        } else {
            System.err.println("Serialization/Connection Error");
        }
    });
}



---

## 4. TROUBLESHOOTING & KEY CONCEPTS

Auto-Creation: If a topic isn't defined via a Bean, Kafka creates it with 1 partition by default (if `auto.create.topics.enable` is true).
* **TimeoutException:** Occurs if you target a non-existent partition index.
* **ClassCastException:** Happens if the Serializer type in `properties` does not match the Java type being sent in `template.send()`.
* **Consumer Prep:** For JSON objects, always set `spring.kafka.consumer.properties.spring.json.trusted.packages=*` or your specific DTO package.

---


server.port=8081


spring.kafka.bootstrap-servers=localhost:9092


spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.properties.spring.json.trusted.packages=com.example.kafka.dto
