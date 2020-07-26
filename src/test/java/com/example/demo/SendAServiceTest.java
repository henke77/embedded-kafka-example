package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class SendAServiceTest extends AbstractServiceTest {
    public static final String TOPIC_A = "topic-a";
    public static final String MESSAGE = "message-a";
    @Autowired
    private SendAService sendAService;

    @BeforeEach
    public void init() {
        embeddedKafkaBroker.addTopics(TOPIC_A);
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TOPIC_A);
    }

    @Test
    public void shouldSend() {
        sendAService.sendMessage(MESSAGE);

        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC_A);

        assertThat(singleRecord.value()).isEqualTo(MESSAGE);
    }
}
