package com.bot.checklistbot.handling;

import java.util.List;

import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.model.userstate.UserData;

/**
 * Менеджер, управляющий работой с {@link BotCommand командами пользователя}
 */
public interface CommandManager {
    /**
     * @return все зарегистрированные в приложении команды пользователя
     */
    List<BotCommand> getAllCommands();

    /**
     * Возвращает команду, соответствующую контексту полученному от пользователя
     * @param userData данные о состоянии пользователя
     * @param message отправленное пользователем сообщение
     * @return команда, соответствующая контексту, если из контекста невозможно извлечь команду, будет возвращена
     *         {@link com.bot.checklistbot.handling.commands.ExceptionCommand}
     */
    BotCommand getCommandByContext(UserData userData, BotMessage message);
}
