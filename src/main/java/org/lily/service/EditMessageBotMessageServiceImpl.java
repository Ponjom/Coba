package org.lily.service;

import lombok.RequiredArgsConstructor;
import org.lily.dao.CourseDao;
import org.lily.entity.Course;
import org.lily.template.TelegramMessageKeyboardTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@PropertySource("classpath:telegram/bot.properties")
public class EditMessageBotMessageServiceImpl implements EditMessageBotMessageService {
    private final TelegramLongPollingBot telegramLongPollingBot;
    private final CourseDao courseDao;
    private final TelegramMessageKeyboardTemplate telegramMessageKeyboardTemplate;
    private final SendBotMessageService sendBotMessageService;

    @Value("${telegram.channel.id}")
    private String TELEGRAM_CHANNEL_ID;
    private static final Logger LOGGER = LoggerFactory.getLogger(EditMessageBotMessageServiceImpl.class);

    @Override
    @NotNull
    public void setMessage(Message message, String messageText) {
        if (message.getCaption() != null) {
            sendBotMessageService.sendMessage(message.getChat(), messageText);
            deleteMessage(message);
            return;
        }
        var editMessage = new EditMessageText();

        editMessage.setChatId(message.getChatId());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(messageText);

        try {
            telegramLongPollingBot.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public void setCourseList(Message message, String messageText) {
        if (message.getCaption() != null) {
            sendBotMessageService.sendCourseListMessage(message.getChat(), messageText);
            deleteMessage(message);
            return;
        }
        List<Course> courses = courseDao.getAllCourses();
        InlineKeyboardMarkup keyboardMarkup = telegramMessageKeyboardTemplate.getCoursesList(courses);

        var editMessage = new EditMessageText();

        editMessage.setChatId(message.getChatId());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(messageText);
        editMessage.setReplyMarkup(keyboardMarkup);

        try {
            telegramLongPollingBot.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public void setCourse(Message message, Course course) {
        var deleteMessage = new DeleteMessage();

        deleteMessage.setChatId(message.getChatId());
        deleteMessage.setMessageId(message.getMessageId());

        var editMessage = new SendPhoto();

        editMessage.setChatId(message.getChatId());
        editMessage.setCaption("Курс: " + course.getName() + "\n\n" + course.getDescription());
        editMessage.setPhoto(new InputFile("http://lily.pw/storage/" + course.getImagePath()));

        editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getCourse());

        try {
            telegramLongPollingBot.executeAsync(editMessage);
            telegramLongPollingBot.executeAsync(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public void setStartMenu(Message message, String messageText) {
        if (message.getCaption() != null) {
            sendBotMessageService.sendStartMessage(message.getChat(), messageText);
            deleteMessage(message);
            return;
        }

        var editMessage = new EditMessageText();

        editMessage.setChatId(message.getChatId());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(messageText);
        editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getStartMessageKeyboard());

        try {
            telegramLongPollingBot.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUnknown(Message message, String messageText) {
        if (message.getCaption() != null) {
            sendBotMessageService.sendUnknownMessage(message.getChat(), messageText);
            deleteMessage(message);
            return;
        }

        var editMessage = new EditMessageText();

        editMessage.setChatId(message.getChatId());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(messageText);
        editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getToStart());

        try {
            telegramLongPollingBot.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setManual(Message message, String messageSuccessText, String messageFailedText, Long userId) {
        var editMessage = new EditMessageText();
        ChatMember chatMember;
        try {
            GetChatMember getChatMember = new GetChatMember();
            getChatMember.setChatId(TELEGRAM_CHANNEL_ID);
            getChatMember.setUserId(userId);

            chatMember = telegramLongPollingBot.execute(getChatMember);
            LOGGER.info(chatMember.toString());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        editMessage.setChatId(message.getChatId());
        editMessage.setParseMode("HTML");
        editMessage.setDisableWebPagePreview(false);
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(messageSuccessText);
        editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getToStart());
        if (
                chatMember.getStatus().equals("left") ||
                chatMember.getStatus().equals("kicked") ||
                chatMember.getStatus().equals("restricted")
        ) {
            editMessage.setText(messageFailedText);
            editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getManualCheck());
        }

        try {
            if (!Objects.equals(message.getText(), messageFailedText)) {
                telegramLongPollingBot.execute(editMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setManualCheck(Message message, String messageSuccessText, String messageFailedText, Long userId) {
        var editMessage = new EditMessageText();

        ChatMember chatMember;
        try {
            GetChatMember getChatMember = new GetChatMember();
            getChatMember.setChatId(TELEGRAM_CHANNEL_ID);
            getChatMember.setUserId(userId);

            chatMember = telegramLongPollingBot.execute(getChatMember);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        editMessage.setChatId(message.getChatId());
        editMessage.setParseMode("HTML");
        editMessage.setDisableWebPagePreview(false);
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(messageSuccessText);
        editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getToStart());
        if (
                chatMember.getStatus().equals("left") ||
                chatMember.getStatus().equals("kicked") ||
                chatMember.getStatus().equals("restricted")
        ) {
            editMessage.setText(messageFailedText);
            editMessage.setReplyMarkup(telegramMessageKeyboardTemplate.getManualCheck());
        }

        try {
            if (!Objects.equals(editMessage.getText(), messageFailedText)) {
                telegramLongPollingBot.execute(editMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMessage(Message message) {
        var deleteMessage = new DeleteMessage();

        deleteMessage.setChatId(message.getChatId());
        deleteMessage.setMessageId(message.getMessageId());

        try {
            telegramLongPollingBot.executeAsync(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
