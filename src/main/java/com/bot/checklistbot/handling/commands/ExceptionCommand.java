package com.bot.checklistbot.handling.commands;

import java.util.Collections;

import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;

/**
 * Команда, предоставляющая пользователю читаемое сообщение об ошибке выполнения
 */
public class ExceptionCommand implements BotCommand {
    private final UserData userData;
    private final String messageForUser;

    public ExceptionCommand(UserData userData, String messageForUser) {
        this.userData = userData;
        this.messageForUser = messageForUser;
    }

    @Override
    public boolean isValid(UserData userData) {
        return true;
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException { }

    @Override
    public String getCode() {
        return Commands.EXCEPTION_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        return new BotResponse(userData.getId(), Collections.singletonList(messageForUser));
    }
}
