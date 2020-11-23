package com.bot.checklistbot.handling;

import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.bot.BotResponse;

/**
 * Интерфейс, описывающий поведение обработчика сообщений от бота
 */
@FunctionalInterface
public interface RequestHandler {
    /**
     * Обработка сообщения от пользователя
     * @param message сообщение, полученное от пользователя
     * @return ответ, который будет возвращён пользователю
     */
    BotResponse handle(BotMessage message);
}
