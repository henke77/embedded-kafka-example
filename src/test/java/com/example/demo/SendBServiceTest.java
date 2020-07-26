package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class SendBServiceTest extends AbstractServiceTest {
    public static final String TOPIC_B = "topic-b";
    public static final String MESSAGE = "message-b";
    @Autowired
    private SendBService sendBService;

    @BeforeEach
    public void init() {
        embeddedKafkaBroker.addTopics(TOPIC_B);
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TOPIC_B);
    }

    @Test
    public void shouldSend() {
        sendBService.sendMessage(MESSAGE);

        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC_B);

        assertThat(singleRecord.value()).isEqualTo(MESSAGE);
    }
}
