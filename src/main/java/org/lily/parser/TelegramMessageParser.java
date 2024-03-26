package org.lily.parser;

import org.springframework.stereotype.Component;

@Component
public class TelegramMessageParser {
    public String parseCommand(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        if (message.charAt(0) != '/') {
            return null;
        }

        String[] parts = message.split(" ");
        if (parts.length > 0) {
            return parts[0].toLowerCase();
        }

        return null;
    }

    public String parseCallbackQuery(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        String[] parts = message.split("_");
        if (parts.length > 0) {
            return parts[0];
        }

        return null;
    }

    public String getFirstParameter(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        String[] parts = message.split("_");
        if (parts.length > 1) {
            return parts[1];
        }

        return null;
    }
}
