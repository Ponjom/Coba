package org.coba.factory;

import org.coba.bot.TelegramBotCoba;
import org.coba.command.Command;
import org.coba.command.CommandName;
import org.coba.command.StartCommand;
import org.coba.command.UnknownCommand;
import org.coba.service.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class CommandFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotCoba.class);
    private StartCommand startCommand;
    private UnknownCommand unknownCommand;

    public Command createCommand(String commandIdentifier) {
        if (Objects.equals(commandIdentifier, CommandName.START.getCommandName())) {
            return startCommand;
        }
        return unknownCommand;
    }

    @Autowired
    public void setStartCommand(StartCommand startCommand) {
        this.startCommand = startCommand;
    }

    @Autowired
    public void setUnknownCommand(UnknownCommand unknownCommand) {
        this.unknownCommand = unknownCommand;
    }
}