package org.coba.config;

import org.coba.bot.TelegramBotCoba;
import org.coba.command.Command;
import org.coba.command.StartCommand;
import org.coba.command.UnknownCommand;
import org.coba.factory.CommandFactory;
import org.coba.service.SendBotMessageService;
import org.coba.service.SendBotMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@PropertySource("classpath:telegram/bot.properties")
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
    public CommandFactory commandFactory() {
        return new CommandFactory();
    }

    @Bean
    public SendBotMessageService sendBotMessageService() {
        return new SendBotMessageServiceImpl();
    }

    @Bean
    public TelegramLongPollingBot TelegramLongPollingBotProd(DefaultBotOptions botOptions, CommandFactory commandFactory) {
        return new TelegramBotCoba(botOptions, TELEGRAM_BOT_TOKEN, commandFactory);
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

    @Bean
    public StartCommand startCommand() {
        return new StartCommand();
    }

    @Bean
    public UnknownCommand unknownCommand() {
        return new UnknownCommand();
    }
}