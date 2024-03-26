package org.lily.template;

import org.lily.entity.Course;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface TelegramMessageKeyboardTemplate {
    InlineKeyboardMarkup getStartMessageKeyboard();
    InlineKeyboardMarkup getCoursesList(List<Course> course);
    InlineKeyboardMarkup getCourse();
    InlineKeyboardMarkup getToStart();
    InlineKeyboardMarkup getManualCheck();
}
