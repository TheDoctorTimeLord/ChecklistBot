package com.bot.checklistbot.handling.commands.checklist;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistService;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserDataService;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.UserStateMenu;

/**
 * Команда удаления списка пользователя
 */
@Command
public class DeleteChecklistCommand implements BotCommand {
    private final UserDataService userDataService;
    private final ChecklistService checklistService;
    private Checklist checklist;
    private UserData userData;

    @Autowired
    public DeleteChecklistCommand(UserDataService userDataService, ChecklistService checklistService) {
        this.userDataService = userDataService;
        this.checklistService = checklistService;
    }

    @Override
    public boolean isValid(UserData userData) {
        UserState state = userData.getState();
        return UserStateMenu.VIEW_CHECKLIST.isMenu(state);
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (metadata != null && !metadata.isEmpty()) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }

        Checklist checklist = checklistService.find(userData.getState().getChecklistId());
        if (checklist == null || userData.getId() != checklist.getOwner().getId()) {
            throw new CommandInitializeException(LocalizedText.CHECKLIST_NOT_FOUND);
        }

        this.checklist = checklist;
        this.userData = userData;
    }

    @Override
    public String getCode() {
        return Commands.DELETE_CHECKLIST_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        userDataService.changeUserState(userData, new UserState(null, null));
        checklistService.delete(checklist);

        return new BotResponse(userData.getId(), Collections.singletonList(LocalizedText.deleteSuccessful(
                checklist.getTitle())));
    }
}
