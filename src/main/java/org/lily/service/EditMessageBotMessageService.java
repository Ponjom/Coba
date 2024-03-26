package org.lily.service;

import org.lily.entity.Course;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface EditMessageBotMessageService {
    void setMessage(Message message, String messageText);
    void setCourseList(Message message, String messageText);
    void setCourse(Message message, Course course);
    void setStartMenu(Message message, String messageText);
    void setUnknown(Message message, String messageText);
    void setManual(Message message, String messageSuccessText, String messageFailedText, Long userId);
    void setManualCheck(Message message, String messageSuccessText, String messageFailedText, Long userId);
}
