package com.bot.checklistbot.handling.commands.checklist;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistService;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.UserStateMenu;
import com.bot.checklistbot.serviceclasses.parsing.InputParser;
import com.bot.checklistbot.serviceclasses.parsing.InputParserBuilder;

/**
 * Редактирование списка пользователя
 */
@Command
public class EditChecklistCommand implements BotCommand {
    private static final String CHECKLIST_CAPTURE = "checklistCapture";
    private static final InputParser COMMAND_ARGS = InputParserBuilder.builder()
            .bindToStartOfLine()
            .addElement(CHECKLIST_CAPTURE)
            .build();

    private final ChecklistService checklistService;
    private Checklist checklist;
    private String capture;
    private UserData userData;

    @Autowired
    public EditChecklistCommand(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @Override
    public boolean isValid(UserData userData) {
        UserState state = userData.getState();
        return UserStateMenu.VIEW_CHECKLIST.isMenu(state);
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (!COMMAND_ARGS.parse(metadata)) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }

        Checklist checklist = checklistService.find(userData.getState().getChecklistId());
        if (checklist == null || userData.getId() != checklist.getOwner().getId()) {
            throw new CommandInitializeException(LocalizedText.CHECKLIST_NOT_FOUND);
        }

        this.checklist = checklist;
        this.capture = COMMAND_ARGS.getElement(CHECKLIST_CAPTURE);
        this.userData = userData;
    }

    @Override
    public String getCode()
    {
        return Commands.EDIT_CHECKLIST_COMMAND_CODE;
    }

    @Override
    public BotResponse execute()
    {
        checklist.setCapture(capture);
        checklistService.save(checklist);

        return new BotResponse(userData.getId(), Collections.singletonList(LocalizedText.saveSuccessful(
                checklist.getTitle())));
    }
}
