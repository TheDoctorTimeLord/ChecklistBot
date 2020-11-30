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

    /**
     * Коды команд
     */
    interface Commands {
        String EXCEPTION_COMMAND_CODE = "exceptionCommand";
        String VIEW_CHECKLIST_COMMAND_CODE = "Показать";
        String ADD_CHECKLIST_COMMAND_CODE = "Добавить список";
        String EDIT_CHECKLIST_COMMAND_CODE = "Изменить список";
        String DELETE_CHECKLIST_COMMAND_CODE = "Удалить список";
        String ADD_CHECKLIST_ITEM_COMMAND_CODE = "Добавить пункт";
        String DELETE_CHECKLIST_ITEM_COMMAND_CODE = "Удалить пункт";
        String VIEW_CHECKLIST_ITEM_COMMAND_CODE = "Показать пункт";
        String CHANGE_CHECKLIST_ITEM_COMMAND_CODE = "Изменить";
        String BACK_MENU_COMMAND_CODE = "Назад";
        String LIST_COMMAND_CODE = "Команды";
        String DELIMITER = "!";
    }

    interface Schedule {
        String TIME_ZONE = "Asia/Yekaterinburg";
    }
}
