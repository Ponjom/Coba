package org.lily.bot;

import lombok.Setter;
import org.lily.callbackQuery.CallbackQuery;
import org.lily.command.Command;
import org.lily.factory.CallbackQueryFactory;
import org.lily.factory.CommandFactory;
import org.lily.parser.TelegramMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Setter
public class TelegramBotCoba extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotCoba.class);
    private CommandFactory commandFactory;
    private CallbackQueryFactory callbackQueryFactory;
    private final TelegramMessageParser telegramMessageParser;
    public TelegramBotCoba(DefaultBotOptions botOptions, String token) {
        super(botOptions, token);
        this.telegramMessageParser = new TelegramMessageParser();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().isCommand()) {
            Command command = commandFactory.getCommandByCommandName(telegramMessageParser.parseCommand(update.getMessage().getText()));

            command.execute(update);
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = callbackQueryFactory.getCallbackQueryByMessage(telegramMessageParser.parseCallbackQuery(update.getCallbackQuery().getData()));

            LOGGER.info("CallbackQuery: {}", update.getCallbackQuery().getData());
            callbackQuery.execute(update);
        }
    }
    @Override
    public String getBotUsername() {
        return "kkk_bot";
    }
}