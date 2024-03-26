package org.consumer.kafka.config;

import org.consumer.kafka.bot.TelegramBotDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Configuration
@PropertySource("classpath:telegram/bot.properties")
@ComponentScan(basePackages = {"org.consumer.kafka.service"})
public class TelegramBotConfig {
    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;
    @Value("${telegram.bot.name}")
    private String TELEGRAM_BOT_NAME;
    @Bean
    public TelegramLongPollingBot telegramBot() {
        return new TelegramBotDefault(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }
}
