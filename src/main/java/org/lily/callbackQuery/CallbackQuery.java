package org.lily.callbackQuery;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQuery {
    void execute(Update update);
}
