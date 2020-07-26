package com.example.demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@EmbeddedKafka(ports = {9092})
@TestConfiguration
public abstract class AbstractServiceTest {
    @Autowired
    protected EmbeddedKafkaBroker embeddedKafkaBroker;
    protected Consumer<Integer, String> consumer;

    @BeforeEach
    public void superInit() {
        consumer = consumerFactory().createConsumer(UUID.randomUUID().toString(), "test");
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(), new IntegerDeserializer(), new StringDeserializer());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return new HashMap<>(KafkaTestUtils
                .consumerProps(UUID.randomUUID().toString(), "false", embeddedKafkaBroker));
    }
}