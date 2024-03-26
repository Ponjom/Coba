package org.consumer.kafka.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.consumer.kafka.content.MessageContent;
import org.consumer.kafka.service.SendBotMessageService;
import org.lily.bot.TelegramBotCoba;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerRunnerImpl implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerRunnerImpl.class);
    private final KafkaConsumer<String, String> consumer;
    private final String topic;
    private final String channelId;

    private final SendBotMessageService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerRunnerImpl(String brokers, String groupId, String topic, String channelId, SendBotMessageService service) {
        this.channelId = channelId;
        this.topic = topic;
        this.service = service;

        Properties props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(Collections.singletonList(this.topic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String jsonMessage = record.value();
                    MessageContent messageContent = objectMapper.readValue(jsonMessage, MessageContent.class);
                    LOGGER.info("Создаем сообщение в группе: {}", messageContent.getBody());
                    service.sendMessage(Long.parseLong(channelId), messageContent.getBody());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}