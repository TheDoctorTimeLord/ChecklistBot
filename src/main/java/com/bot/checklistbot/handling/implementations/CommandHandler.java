package com.bot.checklistbot.handling.implementations;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.CommandManager;
import com.bot.checklistbot.handling.RequestHandler;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserDataService;
import com.bot.checklistbot.serviceclasses.LocalizedText;

/**
 * Обработчик команд пользователя. Интерпретирует сообщения пользователя, как команды
 */
@Component
@Primary
public class CommandHandler implements RequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CommandHandler.class);

    private final UserDataService userDataService;
    private final CommandManager commandManager;

    @Autowired
    public CommandHandler(UserDataService userDataService, CommandManager commandManager) {
        this.userDataService = userDataService;
        this.commandManager = commandManager;
    }

    @Override
    public BotResponse handle(BotMessage message) {
        UserData userData = userDataService.findOrSave(message.getUserId());
        BotCommand command = commandManager.getCommandByContext(userData, message);
        try {
            return command.execute();
        } catch (Exception e) {
            LOG.error(LocalizedText.ERROR, e);
            return new BotResponse(message.getUserId(), Collections.singletonList(LocalizedText.ERROR));
        }
    }
}
