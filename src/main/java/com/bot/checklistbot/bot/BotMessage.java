package com.bot.checklistbot.bot;

import org.springframework.lang.Nullable;

/**
 * Общее представление для сообщений, которые были получены от пользователя через бота
 */
public class BotMessage {
    private final long userId;
    private final String message;

    public BotMessage(long userId, @Nullable String message) {
        this.userId = userId;
        this.message = message;
    }

    /**
     * @return пользователь, отправивший сообщение
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @return текст сообщения, полученный от пользователя
     */
    @Nullable
    public String getMessage() {
        return message;
    }
}
