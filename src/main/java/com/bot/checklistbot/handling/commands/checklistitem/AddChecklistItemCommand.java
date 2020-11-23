package com.bot.checklistbot.handling.commands.checklistitem;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistItem;
import com.bot.checklistbot.model.checklists.ChecklistService;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.UserStateMenu;
import com.bot.checklistbot.serviceclasses.parsing.InputParser;
import com.bot.checklistbot.serviceclasses.parsing.InputParserBuilder;

/**
 * Команда, осуществляющая добавление пункта в список пользователя
 */
@Command
public class AddChecklistItemCommand implements BotCommand {
    private static final String CAPTION = "caption";
    private static final InputParser COMMAND_ARGS = InputParserBuilder.builder()
            .bindToStartOfLine()
            .addElement(CAPTION)
            .build();

    private String capture;
    private UserData userData;
    private Checklist checklist;

    private final ChecklistService checklistService;

    @Autowired
    public AddChecklistItemCommand(ChecklistService checklistService)
    {
        this.checklistService = checklistService;
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

        capture = COMMAND_ARGS.getElement(CAPTION);
        Long checklistId = userData.getState().getChecklistId();

        Checklist checklist = checklistService.find(checklistId);
        if (checklist == null || checklist.getOwner().getId() != userData.getId()) {
            throw new CommandInitializeException(LocalizedText.CHECKLIST_NOT_FOUND);
        }

        this.checklist = checklist;
        this.userData = userData;
    }

    @Override
    public String getCode() {
        return Constants.Commands.ADD_CHECKLIST_ITEM_COMMAND_CODE;
    }

    @Override
    public BotResponse execute()
    {
        checklistService.addItem(new ChecklistItem(capture), checklist);

        return new BotResponse(userData.getId(), Collections.singletonList(LocalizedText.saveSuccessful(capture)));
    }
}
