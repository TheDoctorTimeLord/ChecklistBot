package com.bot.checklistbot.handling;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;

/**
 * Команда, принятая на исполнение от пользователя
 */
public interface BotCommand {
    /**
     * Проверяет возможность выполнения команды в текущем контексте меню пользователя
     * @param userData данные об пользователе, запрашивающего исполнение команды
     * @return true - если команду выполнить можно, false - иначе
     */
    boolean isValid(UserData userData);

    /**
     * Подготавливает команду к запуску
     * @param userData данные об пользователе, запрашивающего исполнение команды
     * @param metadata дополнительные данные для исполнения командой
     * @throws CommandInitializeException команда не может быть проинициализирована с полученными данными
     */
    void initialize(UserData userData, String metadata) throws CommandInitializeException;

    /**
     * @return Уникальный идентификатор команды
     */
    String getCode();

    /**
     * Выполняет проинициализированную команду
     * @return Результат выполнения команды, который должен быть отправлен пользователю
     */
    BotResponse execute();
}
