package org.consumer.kafka.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{
    private final TelegramLongPollingBot telegramLongPollingBot;

    public SendBotMessageServiceImpl(TelegramLongPollingBot telegramLongPollingBot) {
        this.telegramLongPollingBot = telegramLongPollingBot;
    }

    @Override
    public void sendMessage(Long chatId, String messageText) {
        var sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(messageText);

        try {
            telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
