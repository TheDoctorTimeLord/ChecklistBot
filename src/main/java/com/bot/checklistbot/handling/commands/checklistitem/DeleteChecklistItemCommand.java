package com.bot.checklistbot.handling.commands.checklistitem;

import java.util.Collections;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.checklists.ChecklistItem;
import com.bot.checklistbot.model.checklists.ChecklistItemService;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserDataService;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.UserStateMenu;
import com.bot.checklistbot.serviceclasses.parsing.InputParser;
import com.bot.checklistbot.serviceclasses.parsing.InputParserBuilder;

/**
 * Команда удаления пункта из списка пользователя
 */
@Command
public class DeleteChecklistItemCommand implements BotCommand {
    private static final String ITEM_CAPTURE = "capture";
    private static final InputParser COMMAND_ARGS = InputParserBuilder.builder()
            .bindToStartOfLine()
            .optionalPart()
            .addElement(ITEM_CAPTURE)
            .build();
    private final UserDataService userDataService;
    private final ChecklistItemService checklistItemService;
    private ChecklistItem checklistItem;
    private UserData userData;

    public DeleteChecklistItemCommand(UserDataService userDataService, ChecklistItemService checklistItemService)
    {
        this.userDataService = userDataService;
        this.checklistItemService = checklistItemService;
    }

    @Override
    public boolean isValid(UserData userData) {
        UserState state = userData.getState();
        return UserStateMenu.VIEW_CHECKLIST.isMenu(state) || UserStateMenu.VIEW_CHECKLIST_ITEM.isMenu(state);
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (!COMMAND_ARGS.parse(metadata)) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }

        Long checklistItemId = userData.getState().getChecklistItemId();
        ChecklistItem checklistItem;
        if (checklistItemId == null) {
            String capture = COMMAND_ARGS.getElement(ITEM_CAPTURE);
            if (capture == null) {
                throw new CommandInitializeException(LocalizedText.NOT_HAVE_DATA);
            }
            checklistItem = checklistItemService.findByCapture(capture);
        } else {
            checklistItem = checklistItemService.find(checklistItemId);
        }

        if (checklistItem == null) {
            throw new CommandInitializeException(LocalizedText.NOT_HAVE_DATA);
        }

        this.checklistItem = checklistItem;
        this.userData = userData;
    }

    @Override
    public String getCode() {
        return Commands.DELETE_CHECKLIST_ITEM_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        userDataService.changeUserState(userData, new UserState(checklistItem.getChecklist().getId(), null));
        checklistItemService.delete(checklistItem);

        return new BotResponse(userData.getId(), Collections.singletonList(
                LocalizedText.deleteSuccessful(checklistItem.getCapture())));
    }
}
