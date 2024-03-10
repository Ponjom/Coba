package org.coba.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    public void handleException(Exception e) {
        if (e instanceof TelegramApiException) {
            LOGGER.error("Telegram API exception occurred", e);
        } else {
            LOGGER.error("An unexpected exception occurred", e);
        }
    }
}
