package org.lily.command;

import org.lily.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements Command {
    public static final String MESSAGE = "Доступные команды: \n\n/start - главное меню\n/menu - список курсов";
    private final SendBotMessageService sendBotMessageService;
    @Autowired
    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChat(), MESSAGE);
    }
}