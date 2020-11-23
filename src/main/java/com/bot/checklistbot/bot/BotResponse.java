package com.bot.checklistbot.bot;

import java.util.List;

/**
 * Общее представление ответа пользователю для любого бота
 */
public class BotResponse {
    private final boolean enableHtml;
    private final long userId;
    private final List<String> response;

    public BotResponse(long userId, List<String> response, boolean enableHtml) {
        this.userId = userId;
        this.response = response;
        this.enableHtml = enableHtml;
    }

    public BotResponse(long userId, List<String> response) {
        this(userId, response, false);
    }

    /**
     * @return идентификатор пользователя, которому будет доставляться сообщение
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @return текст сообщения, которое будет доставляться
     */
    public List<String> getResponse() {
        return response;
    }

    /**
     * Требуется ли показывать сообщения пользователю в виде html
     * @return true - требуется, false - иначе
     */
    public boolean isEnableHtml() {
        return enableHtml;
    }
}
