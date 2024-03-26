package org.lily.callbackQuery;

import org.lily.service.EditMessageBotMessageService;
import org.lily.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MenuCallbackQuery implements CallbackQuery {
    public static final String MESSAGE = "Список наших курсов:";
    private final EditMessageBotMessageService editMessageBotMessageService;
    @Autowired
    public MenuCallbackQuery(EditMessageBotMessageService editMessageBotMessageService) {
        this.editMessageBotMessageService = editMessageBotMessageService;
    }

    @Override
    public void execute(Update update) {
        var message = update.getCallbackQuery().getMessage();
        if (message instanceof Message) {
            editMessageBotMessageService.setCourseList((Message) message, MESSAGE);
        }
    }
}