package com.bot.checklistbot.handling.commands.checklist;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserDataService;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.UserStateMenu;
import com.bot.checklistbot.serviceclasses.parsing.InputParser;
import com.bot.checklistbot.serviceclasses.parsing.InputParserBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * Команда, осуществляющая добавление списка пользователя
 */
@Command
public class AddChecklistCommand implements BotCommand {
    private static final String TITLE = "title";
    private static final String CAPTION = "caption";
    private static final InputParser COMMAND_ARGS = InputParserBuilder.builder()
            .bindToStartOfLine()
            .addElement(TITLE)
            .optionalPart()
            .addDelimiter(Commands.DELIMITER)
            .addElement(CAPTION)
            .build();

    private final UserDataService userDataService;
    private String title;
    private String caption;
    private UserData userData;

    @Autowired
    public AddChecklistCommand(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public boolean isValid(UserData userData) {
        UserState state = userData.getState();
        return UserStateMenu.START.isMenu(state) || UserStateMenu.VIEW_CHECKLIST.isMenu(state);
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (!COMMAND_ARGS.parse(metadata)) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }

        this.title = COMMAND_ARGS.getElement(TITLE);
        this.caption = COMMAND_ARGS.getElement(CAPTION);
        this.userData = userData;
    }

    @Override
    public String getCode() {
        return Commands.ADD_CHECKLIST_COMMAND_CODE;
    }

    @Override
    public BotResponse execute()
    {
        userDataService.addChecklist(userData, new Checklist(title, caption));

        return new BotResponse(userData.getId(), Collections.singletonList(LocalizedText.saveSuccessful(title)));
    }
}
