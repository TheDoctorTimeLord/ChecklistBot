package com.bot.checklistbot.serviceclasses;

/**
 * Константы, используемые в коде
 */
public interface Constants {
    /**
     * Текстовые константы, предназначенные для ботов
     */
    interface Bot {
        /**
         * Таймаут для операций бота
         */
        int TIMEOUT = 75 * 1000;

        /**
         * Разделитель строк в ответе бота
         */
        String STRING_DELIMITER = "\n";
    }
}
