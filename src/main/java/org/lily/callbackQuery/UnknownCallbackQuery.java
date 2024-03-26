package org.lily.callbackQuery;

import org.lily.service.EditMessageBotMessageService;
import org.lily.service.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownCallbackQuery implements CallbackQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownCallbackQuery.class);
    public static final String MESSAGE = "Не понимаю вас \uD83D\uDE1F, напишите /help чтобы узнать что я понимаю.";
    private final EditMessageBotMessageService editMessageBotMessageService;
    @Autowired
    public UnknownCallbackQuery(EditMessageBotMessageService editMessageBotMessageService) {
        this.editMessageBotMessageService = editMessageBotMessageService;
    }

    @Override
    public void execute(Update update) {
        var message = update.getCallbackQuery().getMessage();
        if (message instanceof Message) {
            editMessageBotMessageService.setUnknown((Message) message, MESSAGE);
        }
    }
}