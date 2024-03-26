package org.lily.callbackQuery;

import org.lily.dao.CourseDao;
import org.lily.parser.TelegramMessageParser;
import org.lily.service.EditMessageBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CourseCallbackQuery implements CallbackQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseCallbackQuery.class);
    public static final String MESSAGE = "Список наших курсов:";
    private final EditMessageBotMessageService editMessageBotMessageService;
    private final CourseDao courseDao;
    private final TelegramMessageParser telegramMessageParser;
    @Autowired
    public CourseCallbackQuery(EditMessageBotMessageService editMessageBotMessageService, CourseDao courseDao, TelegramMessageParser telegramMessageParser) {
        this.editMessageBotMessageService = editMessageBotMessageService;
        this.courseDao = courseDao;
        this.telegramMessageParser = telegramMessageParser;
    }

    @Override
    public void execute(Update update) {
        var message = update.getCallbackQuery().getMessage();
        if (message instanceof Message) {
            var course = courseDao.getCourse(Long.valueOf(telegramMessageParser.getFirstParameter(update.getCallbackQuery().getData())));

            editMessageBotMessageService.setCourse((Message) message, course);
        }
    }
}