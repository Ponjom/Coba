package org.coba;

import org.coba.bot.TelegramBotCoba;
import org.coba.config.TelegramBotConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(TelegramBotConfig.class);
        var TelegramBot = ctx.getBean(TelegramBotCoba.class);
        TelegramBot.onRegister();
    }
}