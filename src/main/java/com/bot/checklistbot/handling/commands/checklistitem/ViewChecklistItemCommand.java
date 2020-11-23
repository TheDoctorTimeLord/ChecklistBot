package com.bot.checklistbot.handling.commands.checklistitem;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
 * Команда для показа определённого элемента списка пользователю
 */
@Command
public class ViewChecklistItemCommand implements BotCommand {
    private static final String CAPTURE = "capture";
    private static final InputParser COMMAND_ARGS = InputParserBuilder.builder()
            .bindToStartOfLine()
            .addElement(CAPTURE)
            .build();
    private final UserDataService userDataService;
    private final ChecklistItemService checklistItemService;
    private ChecklistItem item;
    private UserData userData;

    @Autowired
    public ViewChecklistItemCommand(UserDataService userDataService, ChecklistItemService checklistItemService) {
        this.userDataService = userDataService;
        this.checklistItemService = checklistItemService;
    }

    @Override
    public boolean isValid(UserData userData) {
        return UserStateMenu.VIEW_CHECKLIST.isMenu(userData.getState());
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (!COMMAND_ARGS.parse(metadata)) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }

        String capture = COMMAND_ARGS.getElement(CAPTURE);
        ChecklistItem item = checklistItemService.findByCapture(capture);
        if (item == null || userData.getId() != item.getChecklist().getOwner().getId()) {
            throw new CommandInitializeException(LocalizedText.CHECKLIST_ITEM_NOT_FOUND);
        }

        this.item = item;
        this.userData = userData;
    }

    @Override
    public String getCode() {
        return Commands.VIEW_CHECKLIST_ITEM_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        userDataService.changeUserState(userData, new UserState(item.getChecklist().getId(), item.getId()));

        List<String> answer = new LinkedList<>();
        answer.add(LocalizedText.captureChecklistItem(item.getCapture()));
        answer.add(LocalizedText.stateChecklistItem(item.isState()));

        return new BotResponse(userData.getId(), answer);
    }
}
