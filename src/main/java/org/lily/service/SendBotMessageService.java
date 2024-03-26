package org.lily.service;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface SendBotMessageService {
    void sendMessage(Chat chat, String message);
    void sendCourseListMessage(Chat chat, String message);
    void sendStartMessage(Chat chat, String message);
    void sendUnknownMessage(Chat chat, String message);
}
