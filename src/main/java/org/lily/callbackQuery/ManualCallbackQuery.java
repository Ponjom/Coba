package org.lily.callbackQuery;

import org.lily.service.EditMessageBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ManualCallbackQuery implements CallbackQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManualCallbackQuery.class);
    public static final String SUCCESS_MESSAGE = "Урааа, ты состоишь в группе\uD83E\uDD70\uD83E\uDD70 \n\nВот твои методички: <a href=\"http://lily.pw/storage/%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%B8%D1%87%D0%BA%D0%B8.zip\">\u200F</a>";
    public static final String FAILED_MESSAGE = "Вы не в группе( \n\nВот линка: https://t.me/test_lily_channel";
    private final EditMessageBotMessageService editMessageBotMessageService;
    @Autowired
    public ManualCallbackQuery(EditMessageBotMessageService editMessageBotMessageService) {
        this.editMessageBotMessageService = editMessageBotMessageService;
    }
    @Override
    public void execute(Update update) {
        var message = update.getCallbackQuery().getMessage();
        if (message instanceof Message) {
            editMessageBotMessageService.setManual((Message) message, SUCCESS_MESSAGE, FAILED_MESSAGE, update.getCallbackQuery().getFrom().getId());
        }
    }
}