package org.lily.config;

import org.lily.bot.TelegramBotCoba;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@PropertySource("classpath:telegram/bot.properties")
@Import({HibernateConfig.class})
@ComponentScan(basePackages = {"org.lily.command", "org.lily.callbackQuery", "org.lily.parser", "org.lily.template", "org.lily.factory"})
public class TelegramBotConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotConfig.class);

    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;

    @Value("${telegram.bot.updatesTimeout}")
    private int TELEGRAM_BOT_UPDATES_TIMEOUT;

    @Value("${telegram.bot.updatesLimit}")
    private int TELEGRAM_BOT_UPDATES_LIMIT;

    @Bean
    public DefaultBotOptions telegramBotOptions() {
        try {
            DefaultBotOptions botOptions = new DefaultBotOptions();
            botOptions.setGetUpdatesTimeout(TELEGRAM_BOT_UPDATES_TIMEOUT);
            botOptions.setGetUpdatesLimit(TELEGRAM_BOT_UPDATES_LIMIT);
            return botOptions;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.info("Error set telegram bot options", e);
            return null;
        }
    }
    @Bean
    public TelegramLongPollingBot telegramLongPollingBot(DefaultBotOptions botOptions) {
        return new TelegramBotCoba(botOptions, TELEGRAM_BOT_TOKEN);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingBot TelegramLongPollingBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(TelegramLongPollingBot);
            LOGGER.info("Telegram bot successfully started");
        } catch (TelegramApiException e){
            LOGGER.info("Telegram bot api error", e);
            return null;
        } catch (Exception e) {
            LOGGER.info("Telegram bot register failed", e);
            return null;
        }
        return botsApi;
    }
}