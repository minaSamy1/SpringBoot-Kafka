package com.example.Kafka_Wikimedia_Consumer.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="Wikimedia")
@Setter
@Getter
public class WikimediaData {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;

    @Lob
    private String wikiEventData;
}
