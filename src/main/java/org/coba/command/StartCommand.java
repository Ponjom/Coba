package org.coba.command;

import org.coba.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {
    public static final String UNKNOWN_MESSAGE = "Ну привет, воин";
    private SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }

    @Override
    public String getCommandIdentifier() {
        return CommandName.START.getCommandName();
    }

    @Autowired
    public void setSendBotMessageService(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
}