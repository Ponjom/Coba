package org.lily.factory;

import org.lily.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandFactory {
    private final Map<String, Command> commandMap;

    public CommandFactory(
            StartCommand startCommand,
            HelpCommand helpCommand,
            MenuCommand menuCommand,
            UnknownCommand unknownCommand
    ) {
        this.commandMap = Map.of(
                CommandName.START.getCommandName(), startCommand,
                CommandName.HELP.getCommandName(), helpCommand,
                CommandName.MENU.getCommandName(), menuCommand,
                CommandName.UNKNOWN.getCommandName(), unknownCommand
        );
    }

    public Command getCommandByCommandName(String commandName) {
        return commandMap.getOrDefault(commandName, commandMap.get(CommandName.UNKNOWN.getCommandName()));
    }
}