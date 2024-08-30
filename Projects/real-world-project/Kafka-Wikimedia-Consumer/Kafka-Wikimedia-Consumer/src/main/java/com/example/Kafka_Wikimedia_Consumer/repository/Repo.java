package com.example.Kafka_Wikimedia_Consumer.repository;

import com.example.Kafka_Wikimedia_Consumer.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  Repo extends JpaRepository<WikimediaData, Long> {
}
