package org.lily.command;

import org.lily.service.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownCommand.class);
    public static final String UNKNOWN_MESSAGE = "Не понимаю вас \uD83D\uDE1F, напишите /help чтобы узнать что я понимаю.";

    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        LOGGER.info("Unknown command: {}", update.getMessage().getText());
        sendBotMessageService.sendUnknownMessage(update.getMessage().getChat(), UNKNOWN_MESSAGE);
    }
}