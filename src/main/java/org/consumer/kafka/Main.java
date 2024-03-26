package org.consumer.kafka;

import org.consumer.kafka.config.KafkaConsumerConfig;
import org.consumer.kafka.runner.KafkaConsumerRunnerImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(KafkaConsumerConfig.class);
        var consumer = context.getBean(KafkaConsumerRunnerImpl.class);
        new Thread(consumer).start();
    }
}
