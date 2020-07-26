package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
public class SendBServiceTest extends AbstractServiceTest {
    public static final String TOPIC_B = "test-b";
    @Autowired
    private SendBService sendBService;

    @BeforeEach
    public void init() {
        embeddedKafkaBroker.addTopics(TOPIC_B);
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TOPIC_B);
    }

    @Test
    public void shouldSend() {
        sendBService.sendMessage("test-b");

        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC_B);
        assertThat(singleRecord).isNotNull();
    }
}
