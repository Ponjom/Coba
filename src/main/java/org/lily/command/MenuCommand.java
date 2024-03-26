package org.lily.command;

import org.lily.service.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MenuCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuCommand.class);
    public static final String MESSAGE = "Список наших курсов:";
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public MenuCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendCourseListMessage(update.getMessage().getChat(), MESSAGE);
    }
}