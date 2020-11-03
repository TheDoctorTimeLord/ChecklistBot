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

        /**
         * Путь до конфигурационного файла бота
         */
        String BOT_PROPERTIES = "classpath:bot.properties";
    }

    /**
     * Константы, для работы с package
     */
    interface Package {
        /**
         * Путь до базовой части проекта, в которой нужно проводить поиск аннотаций
         */
        String BASE_PACKAGE = "com.bot.checklistbot";
    }
}
