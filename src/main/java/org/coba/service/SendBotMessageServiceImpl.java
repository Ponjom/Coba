package org.coba.service;

import org.coba.bot.TelegramBotCoba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {
    private TelegramLongPollingBot telegramLongPollingBotProd;

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            telegramLongPollingBotProd.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setTelegramLongPollingBotProd(TelegramLongPollingBot telegramLongPollingBotProd) {
        this.telegramLongPollingBotProd = telegramLongPollingBotProd;
    }
}