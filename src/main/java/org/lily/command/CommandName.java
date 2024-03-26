package org.lily.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    MENU("/menu"),
    UNKNOWN("/unknown");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
