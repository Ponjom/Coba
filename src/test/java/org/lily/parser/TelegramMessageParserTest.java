package org.lily.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TelegramMessageParserTest {
    private TelegramMessageParser telegramMessageParser;
    @BeforeEach
    void setUp() {
        telegramMessageParser = new TelegramMessageParser();
    }
    @Test
    void parseCommand() {
        Assertions.assertNull(telegramMessageParser.parseCommand("test"));
        Assertions.assertNull(telegramMessageParser.parseCommand(null));
        Assertions.assertNull(telegramMessageParser.parseCommand(""));

        Assertions.assertEquals("/test", telegramMessageParser.parseCommand("/test"));
        Assertions.assertEquals("/test", telegramMessageParser.parseCommand("/test 12"));
    }
    @Test
    void parseCallbackQuery() {
        Assertions.assertNull(telegramMessageParser.parseCallbackQuery(null));
        Assertions.assertNull(telegramMessageParser.parseCallbackQuery(""));

        Assertions.assertEquals("test", telegramMessageParser.parseCallbackQuery("test"));
        Assertions.assertEquals("test", telegramMessageParser.parseCallbackQuery("test_asd"));
        Assertions.assertEquals("/test", telegramMessageParser.parseCallbackQuery("/test_12"));
    }
    @Test
    void getFirstParameter() {
        Assertions.assertNull(telegramMessageParser.getFirstParameter(null));
        Assertions.assertNull(telegramMessageParser.getFirstParameter(""));
        Assertions.assertNull(telegramMessageParser.getFirstParameter("/test"));
        Assertions.assertNull(telegramMessageParser.getFirstParameter("/test fs"));

        Assertions.assertEquals("tete", telegramMessageParser.getFirstParameter("/test_tete"));
    }
}
