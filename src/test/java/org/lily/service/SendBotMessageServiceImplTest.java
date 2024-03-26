package org.lily.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lily.dao.CourseDao;
import org.lily.entity.Course;
import org.lily.template.TelegramMessageKeyboardTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendBotMessageServiceImplTest {
    @InjectMocks
    private SendBotMessageServiceImpl sendBotMessageService;

    @Mock
    private TelegramLongPollingBot telegramLongPollingBot;
    @Mock
    private CourseDao courseDao;
    @Mock
    private TelegramMessageKeyboardTemplate telegramMessageKeyboardTemplate;

    @Test
    void sendMessage() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);

        sendBotMessageService.sendMessage(chat, "test");

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendMessage_unknownBot_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);

        Mockito.doThrow(TelegramApiException.class).when(telegramLongPollingBot).execute(any(SendMessage.class));

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendMessage(chat, "test"));

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendMessage_withNullChatId_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(null);

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendMessage(chat, "test"));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendMessage_withNullMessage_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);

        assertThrows(NullPointerException.class, () -> sendBotMessageService.sendMessage(chat, null));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendStartMessage() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);
        Mockito.when(telegramMessageKeyboardTemplate.getStartMessageKeyboard()).thenReturn(new InlineKeyboardMarkup());

        sendBotMessageService.sendStartMessage(chat, "test");

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendStartMessage_unknownBot_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);
        Mockito.when(telegramMessageKeyboardTemplate.getStartMessageKeyboard()).thenReturn(new InlineKeyboardMarkup());

        Mockito.doThrow(TelegramApiException.class).when(telegramLongPollingBot).execute(any(SendMessage.class));

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendStartMessage(chat, "test"));

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendStartMessage_withNullChatId_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(null);

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendStartMessage(chat, "test"));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendStartMessage_withNullMessage_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);

        assertThrows(NullPointerException.class, () -> sendBotMessageService.sendStartMessage(chat, null));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendUnknownMessage() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);

        Mockito.when(telegramMessageKeyboardTemplate.getToStart()).thenReturn(new InlineKeyboardMarkup());

        sendBotMessageService.sendUnknownMessage(chat, "test");

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendUnknownMessage_unknownBot_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);
        Mockito.when(telegramMessageKeyboardTemplate.getToStart()).thenReturn(new InlineKeyboardMarkup());

        Mockito.doThrow(TelegramApiException.class).when(telegramLongPollingBot).execute(any(SendMessage.class));

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendUnknownMessage(chat, "test"));

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendUnknownMessage_withNullChatId_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(null);

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendUnknownMessage(chat, "test"));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendUnknownMessage_withNullMessage_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);

        assertThrows(NullPointerException.class, () -> sendBotMessageService.sendUnknownMessage(chat, null));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendCourseListMessage() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);

        List<Course> courses = List.of(new Course());
        Mockito.when(courseDao.getAllCourses()).thenReturn(courses);

        Mockito.when(telegramMessageKeyboardTemplate.getCoursesList(courses)).thenReturn(new InlineKeyboardMarkup());

        sendBotMessageService.sendCourseListMessage(chat, "test");

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendCourseListMessage_unknownBot_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(123L);

        List<Course> courses = List.of(new Course());
        Mockito.when(courseDao.getAllCourses()).thenReturn(courses);

        Mockito.when(telegramMessageKeyboardTemplate.getCoursesList(courses)).thenReturn(new InlineKeyboardMarkup());

        Mockito.doThrow(TelegramApiException.class).when(telegramLongPollingBot).execute(any(SendMessage.class));

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendCourseListMessage(chat, "test"));

        verify(telegramLongPollingBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendCourseListMessage_withNullChatId_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getId()).thenReturn(null);

        assertThrows(RuntimeException.class, () -> sendBotMessageService.sendCourseListMessage(chat, "test"));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    void sendCourseListMessage_withNullMessage_throwsException() throws TelegramApiException {
        Chat chat = Mockito.mock(Chat.class);

        assertThrows(NullPointerException.class, () -> sendBotMessageService.sendCourseListMessage(chat, null));

        verify(telegramLongPollingBot, times(0)).execute(any(SendMessage.class));
    }
}