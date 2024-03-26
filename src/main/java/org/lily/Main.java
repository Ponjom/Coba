package org.lily;

import org.hibernate.SessionFactory;
import org.lily.bot.TelegramBotCoba;
import org.lily.config.TelegramBotConfig;
import org.lily.factory.CallbackQueryFactory;
import org.lily.factory.CommandFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Инициализация контекста приложения Spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TelegramBotConfig.class);

        // Получение бина бота и фабрики команд из контекста приложения Spring
        var bot = context.getBean(TelegramBotCoba.class);

        // Установка фабрики команд для бота
        var commandFactory = context.getBean(CommandFactory.class);
        var callbackQuery = context.getBean(CallbackQueryFactory.class);

        bot.setCommandFactory(commandFactory);
        bot.setCallbackQueryFactory(callbackQuery);

        // Регистрация бота
        bot.onRegister();
    }
}