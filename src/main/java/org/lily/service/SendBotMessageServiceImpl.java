package org.lily.service;

import lombok.RequiredArgsConstructor;
import org.lily.dao.CourseDao;
import org.lily.entity.Course;
import org.lily.template.TelegramMessageKeyboardTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {
    private final TelegramLongPollingBot telegramLongPollingBot;
    private final CourseDao courseDao;
    private final TelegramMessageKeyboardTemplate telegramMessageKeyboardTemplate;

    @Override
    @NotNull
    public void sendMessage(Chat chat, String message) {
        var sendMessage = new SendMessage();

        sendMessage.setChatId(chat.getId());
        sendMessage.setText(message);

        try {
            telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    @NotNull
    public void sendStartMessage(Chat chat, String messageText) {
        var sendMessage = new SendMessage();

        sendMessage.setChatId(chat.getId());
        sendMessage.enableHtml(true);
        sendMessage.setText(messageText);

        sendMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getStartMessageKeyboard());

        try {
            telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public void sendUnknownMessage(Chat chat, String message) {
        var sendMessage = new SendMessage();

        sendMessage.setChatId(chat.getId());
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getToStart());

        try {
            telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public void sendCourseListMessage(Chat chat, String messageText) {
        List<Course> courses = courseDao.getAllCourses();
        InlineKeyboardMarkup keyboardMarkup = telegramMessageKeyboardTemplate.getCoursesList(courses);

        var sendMessage = new SendMessage();

        sendMessage.setChatId(chat.getId());
        sendMessage.setText(messageText);
        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}