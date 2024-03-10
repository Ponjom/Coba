package org.coba.parser;

public class CommandParser {
    public String parseCommand(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        if (message.charAt(0) != '/') {
            return "";
        }

        String[] parts = message.split(" ");
        if (parts.length > 0) {
            return parts[0].toLowerCase();
        }

        return "";
    }
}
