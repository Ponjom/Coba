package org.lily.callbackQuery;

public enum CallbackQueryName {
    COURSE_MENU("courseMenu"),
    COURSE("course"),
    MANUAL("manual"),
    MANUAL_CHECK("manualCheck"),
    START("start"),
    UNKNOWN("unknown");
    private final String callbackQueryName;

    CallbackQueryName(String callbackQueryName) {
        this.callbackQueryName = callbackQueryName;
    }

    public String getValue() {
        return callbackQueryName;
    }
}
