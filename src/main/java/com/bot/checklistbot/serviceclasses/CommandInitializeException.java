package com.bot.checklistbot.serviceclasses;

/**
 * Общая ошибка для описания возникших проблем при инициализации команды
 */
public class CommandInitializeException extends Exception {
    public CommandInitializeException(String message) {
        super(message);
    }
}
