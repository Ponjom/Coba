package org.coba.bot;

import org.coba.factory.CommandFactory;
import org.coba.service.SendBotMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotCoba extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotCoba.class);
    private final CommandFactory commandFactory;

    public TelegramBotCoba(DefaultBotOptions botOptions, String token, CommandFactory commandFactory) {
        super(botOptions, token);
        this.commandFactory = commandFactory;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("Ну типо привет");
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().isCommand()) {
                String commandIdentifier = update.getMessage().getText().trim().split(" ")[0].toLowerCase();

                commandFactory.createCommand(commandIdentifier).execute(update);
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                sendMessage.setText(update.getMessage().getText());

                try {
                    execute(sendMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public String getBotUsername() {
        return "kkk_bot";
    }
}