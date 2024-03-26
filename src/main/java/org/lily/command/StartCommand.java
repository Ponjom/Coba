package org.lily.command;

import org.lily.service.SendBotMessageService;
import org.lily.service.SendBotMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartCommand.class);
    public static final String MESSAGE = "Приветствую тебя, я Лилу, с чем вам помочь?. \n\nВыбери действие из списка:";
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        LOGGER.info("Выполнена команда {}", update.getMessage().getText());
        sendBotMessageService.sendStartMessage(update.getMessage().getChat(), MESSAGE);
    }
}