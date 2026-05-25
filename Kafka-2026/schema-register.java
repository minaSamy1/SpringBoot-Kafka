Kafka Schema Registry with Spring Boot - Project Documentation
1. Infrastructure Setup (Docker)
The project relies on a Confluent-based infrastructure managed via docker-compose.yml.

Components:
Zookeeper & Broker: The core backbone for Kafka messaging.

Registry: Runs on port 8081. Responsible for managing Avro schemas, ensuring compatibility, and handling serialization/deserialization.

Control Center: UI on port 9021 to monitor topics, view registered schemas, and track message flow.

Setup Steps:
Create docker-compose.yml:

YAML
version: "3"
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:5.4.0
    hostname: zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  broker:
    image: confluentinc/cp-server:5.4.0
    hostname: broker
    container_name: broker
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_METRIC_REPORTERS: io.confluent.metrics.reporter.ConfluentMetricsReporter
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_CONFLUENT_LICENSE_TOPIC_REPLICATION_FACTOR: 1
      CONFLUENT_METRICS_REPORTER_BOOTSTRAP_SERVERS: broker:29092
      CONFLUENT_METRICS_REPORTER_ZOOKEEPER_CONNECT: zookeeper:2181
      CONFLUENT_METRICS_REPORTER_TOPIC_REPLICAS: 1
      CONFLUENT_METRICS_ENABLE: "true"
      CONFLUENT_SUPPORT_CUSTOMER_ID: "anonymous"

  kafka-tools:
    image: confluentinc/cp-kafka:5.4.0
    hostname: kafka-tools
    container_name: kafka-tools
    command: ["tail", "-f", "/dev/null"]
    network_mode: "host"

  schema-registry:
    image: confluentinc/cp-schema-registry:5.4.0
    hostname: schema-registry
    container_name: schema-registry
    depends_on:
      - zookeeper
      - broker
    ports:
      - "8081:8081"
    environment:
      SCHEMA_REGISTRY_HOST_NAME: schema-registry
      SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL: "zookeeper:2181"

  control-center:
    image: confluentinc/cp-enterprise-control-center:5.4.0
    hostname: control-center
    container_name: control-center
    depends_on:
      - zookeeper
      - broker
      - schema-registry
    ports:
      - "9021:9021"
    environment:
      CONTROL_CENTER_BOOTSTRAP_SERVERS: 'broker:29092'
      CONTROL_CENTER_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      CONTROL_CENTER_SCHEMA_REGISTRY_URL: "http://schema-registry:8081"
      CONTROL_CENTER_REPLICATION_FACTOR: 1
      CONTROL_CENTER_INTERNAL_TOPICS_PARTITIONS: 1
      CONTROL_CENTER_MONITORING_INTERCEPTOR_TOPIC_PARTITIONS: 1
      CONFLUENT_METRICS_TOPIC_REPLICATION: 1
      PORT: 9021
Run: docker-compose up -d

Testing:

Registry API: http://localhost:8081/subjects

Confluent UI: http://localhost:9021

2. Project Configuration (Maven)
To support Avro and the Schema Registry, configure your pom.xml as follows:

Properties & Repositories
XML
<properties>
    <java.version>21</java.version>
    <avro.version>1.12.0</avro.version>
    <confluent.version>7.7.0</confluent.version>
</properties>

<repositories>
    <repository>
        <id>confluent</id>
        <url>https://packages.confluent.io/maven/</url>
    </repository>
</repositories>
Dependencies
XML
<dependency>
    <groupId>io.confluent</groupId>
    <artifactId>kafka-avro-serializer</artifactId>
    <version>${confluent.version}</version>
</dependency>
<dependency>
    <groupId>io.confluent</groupId>
    <artifactId>kafka-schema-registry-client</artifactId>
    <version>${confluent.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro</artifactId>
    <version>${avro.version}</version>
</dependency>
Avro Maven Plugin
This plugin generates Java classes from .avsc files.

XML
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>${avro.version}</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
            <configuration>
                <sourceDirectory>${project.basedir}/src/main/avro</sourceDirectory>
                <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
3. Data Definition (Avro Schema)
Create src/main/avro/Employee.avsc:

JSON
{
  "namespace": "com.example.schema_registery_avro.dto",
  "type": "record",
  "name": "Employee",
  "fields": [
    { "name": "id", "type": "string" },
    { "name": "firstName", "type": "string" },
    { "name": "lastName", "type": "string" },
    {
      "name": "email",
      "type": ["null", "string"],
      "default": null
    }
  ]
}
4. Application Configuration (application.properties)
Properties
spring.application.name=schema-registery-avro
topic.name=springtboot-schema
server.port=8181

spring.kafka.bootstrap-servers=127.0.0.1:9092

# Producer
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=io.confluent.kafka.serializers.KafkaAvroSerializer
spring.kafka.producer.properties.schema.registry.url=http://127.0.0.1:8081

# Consumer
spring.kafka.consumer.group-id=javatechie-new
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=io.confluent.kafka.serializers.KafkaAvroDeserializer
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.properties.schema.registry.url=http://127.0.0.1:8081
spring.kafka.consumer.properties.specific.avro.reader=true
Note: Run mvn clean install -U to generate the Java POJOs after schema changes.

5. Implementation Details
Producer Service
Java
public void sendEvent(Employee employee) {
    CompletableFuture<SendResult<String, Object>> future = 
        template.send("springtboot-schema", UUID.randomUUID().toString(), employee);
    
    future.whenComplete((result, ex) -> {
        if (ex == null) System.out.println("Message Send Successfully");
        else System.out.println("Error while Sending Message");
    });
}
Kafka Consumer
Java
@KafkaListener(topics = "${topic.name}")
public void read(ConsumerRecord<String, Employee> consumerRecord) {
    System.out.println("Received: " + consumerRecord.value());
}
6. Execution & Verification
Infrastructure: docker-compose up -d.

Build: mvn clean compile.

Run: Start Spring Boot app.

Test:
1- Calling api with the current version of the schema 

localhost:8181/kafka/sendObject
	  {
		"id": "1",
		"firstName": "ghggbdf",
		"lastName": "dssd"
	}
	
	
we found this message is sended to the Topic and Read in Consumer 


Second senario
   - we need to change the schema by adding new Filed on the object for exampl Email 
   
   
    {
      "name": "email",
      "type": ["null", "string"],
      "default": ""
    }
	
//   "type": ["null", "string"] it's mean this Filed it's optinal so you can send with email or not 
 thats mean the consumer can received both version 
 
 
 - after we add this filed we will need to Regenerate the Class again 
  so we will need to run [ mvn clean install -U ] 
  
 - now you can testing the Version 
    //localhost:8081/subjects 
	localhost:8181/kafka/sendObject
	{
    "id": "1",
    "firstName": "ghggbdf",
    "lastName": "dssd",
    "email": "EWEWE@DSD"
   
}

- so and you can check the Version on Schema Register
    [ http://localhost:8081/subjects/springtboot-schema-value/versions ]
	  http://localhost:8081/subjects/springtboot-schema-value/versions/2 ]
	  
	  {
   "subject":"springtboot-schema-value",
   "version":3,
   "id":3,
   "schema":"{\"type\":\"record\",\"name\":\"Employee\",\"namespace\":\"com.example.schema_registery_avro.dto\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"firstName\",\"type\":\"string\"},{\"name\":\"lastName\",\"type\":\"string\"},{\"name\":\"email\",\"type\":[\"null\",\"string\"],\"default\":\"\"}]}"
}
	  