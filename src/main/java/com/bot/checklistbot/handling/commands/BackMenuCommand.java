package com.bot.checklistbot.handling.commands;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserDataService;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.UserStateMenu;

/**
 * Команда возвращение в предыдущее меню
 */
@Command
public class BackMenuCommand implements BotCommand {
    private final UserDataService userDataService;
    private UserData userData;

    @Autowired
    public BackMenuCommand(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public boolean isValid(UserData userData) {
        return !UserStateMenu.START.isMenu(userData.getState());
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (metadata != null && !metadata.isEmpty()) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }
        this.userData = userData;
    }

    @Override
    public String getCode() {
        return Commands.BACK_MENU_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        UserState userState = userData.getState();
        Long checklistId = userState.getChecklistId();
        Long checklistItemId = userState.getChecklistItemId();

        if (checklistItemId != null) {
            checklistItemId = null;
        } else if (checklistId != null) {
            checklistId = null;
        }

        userDataService.changeUserState(userData, new UserState(checklistId, checklistItemId));

        return new BotResponse(userData.getId(), Collections.singletonList(LocalizedText.SUCCESS_COMMAND));
    }
}
