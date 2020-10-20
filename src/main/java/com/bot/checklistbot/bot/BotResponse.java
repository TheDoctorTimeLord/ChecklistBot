package com.bot.checklistbot.bot;

/**
 * Общее представление ответа пользователю для любого бота
 */
public class BotResponse {
    private final long userId;
    private final String[] response;

    public BotResponse(long userId, String[] response) {
        this.userId = userId;
        this.response = response;
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
    public String[] getResponse() {
        return response;
    }
}
