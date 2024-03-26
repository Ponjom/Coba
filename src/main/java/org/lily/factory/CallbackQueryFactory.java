package org.lily.factory;

import org.lily.callbackQuery.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CallbackQueryFactory {
    private final Map<String, CallbackQuery> callbackQueryMap;
    public CallbackQueryFactory(
            MenuCallbackQuery menuCallbackQuery,
            CourseCallbackQuery courseCallbackQuery,
            StartCallbackQuery startCallbackQuery,
            ManualCallbackQuery manualCallbackQuery,
            ManualCheckCallbackQuery manualCheckCallbackQuery,
            UnknownCallbackQuery unknownCallbackQuery
    ) {
        this.callbackQueryMap = Map.of(
                CallbackQueryName.COURSE_MENU.getValue(), menuCallbackQuery,
                CallbackQueryName.COURSE.getValue(), courseCallbackQuery,
                CallbackQueryName.MANUAL.getValue(), manualCallbackQuery,
                CallbackQueryName.START.getValue(), startCallbackQuery,
                CallbackQueryName.MANUAL_CHECK.getValue(), manualCheckCallbackQuery,
                CallbackQueryName.UNKNOWN.getValue(), unknownCallbackQuery
        );
    }
    public CallbackQuery getCallbackQueryByMessage(String callbackQueryName) {
        return callbackQueryMap.getOrDefault(callbackQueryName, callbackQueryMap.get(CallbackQueryName.UNKNOWN.getValue()));
    }
}