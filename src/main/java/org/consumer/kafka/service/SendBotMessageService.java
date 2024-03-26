package org.consumer.kafka.service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public interface SendBotMessageService {
    void sendMessage(Long chatId, String messageText);
}
