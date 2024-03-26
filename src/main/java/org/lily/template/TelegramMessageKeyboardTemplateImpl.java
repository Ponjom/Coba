package org.lily.template;

import org.lily.callbackQuery.CallbackQueryName;
import org.lily.entity.Course;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Component
public class TelegramMessageKeyboardTemplateImpl implements TelegramMessageKeyboardTemplate {
    @Override
    public InlineKeyboardMarkup getStartMessageKeyboard() {
        Map<String, String> KeyboardMap = new LinkedHashMap<>();

        KeyboardMap.put("Список курсов \uD83D\uDCCB", CallbackQueryName.COURSE_MENU.getValue());
        KeyboardMap.put("Получить методичку \uD83C\uDF81", CallbackQueryName.MANUAL.getValue());

        return createInlineKeyboard(KeyboardMap);
    }

    @Override
    public InlineKeyboardMarkup getCourse() {
        Map<String, String> KeyboardMap = new LinkedHashMap<>();

        KeyboardMap.put("Купить курс ⚙\uFE0FВ разработке⚙\uFE0F", CallbackQueryName.UNKNOWN.getValue());
        KeyboardMap.put("Вернуться на главную \uD83C\uDFE0", CallbackQueryName.START.getValue());

        return createInlineKeyboard(KeyboardMap);
    }

    @Override
    public InlineKeyboardMarkup getToStart() {
        Map<String, String> KeyboardMap = new LinkedHashMap<>();

        KeyboardMap.put("Вернуться на главную \uD83C\uDFE0", CallbackQueryName.START.getValue());

        return createInlineKeyboard(KeyboardMap);
    }

    @Override
    public InlineKeyboardMarkup getManualCheck() {
        Map<String, String> KeyboardMap = new LinkedHashMap<>();

        KeyboardMap.put("Проверить еще раз \uD83D\uDD04", CallbackQueryName.MANUAL_CHECK.getValue());
        KeyboardMap.put("Вернуться на главную \uD83C\uDFE0", CallbackQueryName.START.getValue());

        return createInlineKeyboard(KeyboardMap);
    }

    @Override
    public InlineKeyboardMarkup getCoursesList(List<Course> course) {
        Map<String, String> KeyboardMap = new LinkedHashMap<>();

        for (Course c : course) {
            KeyboardMap.put(c.getName(), CallbackQueryName.COURSE.getValue() + "_" + c.getId());
        }

        return createInlineKeyboard(KeyboardMap);
    }

    private InlineKeyboardMarkup createInlineKeyboard(Map<String, String> textCommandMap) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Map.Entry<String, String> entry : textCommandMap.entrySet()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(entry.getKey());
            button.setCallbackData(entry.getValue());
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(button);
            rowList.add(keyboardButtonsRow);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
