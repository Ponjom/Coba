package org.consumer.kafka.config;

import org.consumer.kafka.runner.KafkaConsumerRunnerImpl;
import org.consumer.kafka.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:dms/kafka.properties", "classpath:telegram/bot.properties"})
@Import(TelegramBotConfig.class)
public class KafkaConsumerConfig {
    @Value("${telegram.channel.id}")
    private String TELEGRAM_CHANNEL_ID;
    @Value("${dms.kafka.brokers}")
    private String DMS_KAFKA_BROKERS;
    @Value("${dms.kafka.group.id}")
    private String DMS_KAFKA_GROUP_ID;
    @Value("${dms.kafka.topic}")
    private String DM_KAFKA_TOPIC;

    @Bean
    public KafkaConsumerRunnerImpl kafkaConsumerRunner(SendBotMessageService service) {
        return new KafkaConsumerRunnerImpl(
                DMS_KAFKA_BROKERS,
                DMS_KAFKA_GROUP_ID,
                DM_KAFKA_TOPIC,
                TELEGRAM_CHANNEL_ID,
                service
        );
    }
}
