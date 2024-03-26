package org.lily.callbackQuery;

import org.lily.command.StartCommand;
import org.lily.service.EditMessageBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCallbackQuery implements CallbackQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartCallbackQuery.class);
    public static final String MESSAGE = "Приветствую тебя, я Лилу, с чем вам помочь?. \n\nВыбери действие из списка:";
    private final EditMessageBotMessageService editMessageBotMessageService;
    @Autowired
    public StartCallbackQuery(EditMessageBotMessageService editMessageBotMessageService) {
        this.editMessageBotMessageService = editMessageBotMessageService;
    }

    @Override
    public void execute(Update update) {
        var message = update.getCallbackQuery().getMessage();
        if (message instanceof Message) {
            editMessageBotMessageService.setStartMenu((Message) message, MESSAGE);
        }
    }
}