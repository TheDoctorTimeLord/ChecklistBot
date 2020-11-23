package com.bot.checklistbot.handling.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.handling.CommandManager;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistItem;
import com.bot.checklistbot.model.checklists.ChecklistItemService;
import com.bot.checklistbot.model.checklists.ChecklistService;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants.Commands;
import com.bot.checklistbot.serviceclasses.LocalizedText;

/**
 * Команда для получения доступных команд в текущем контексте
 */
@Command
public class ListCommand implements BotCommand {
    private final ApplicationContext applicationContext;
    private final ChecklistService checklistService;
    private final ChecklistItemService checklistItemService;
    private CommandManager commandManager;
    private UserData userData;

    @Autowired
    public ListCommand(ApplicationContext applicationContext, ChecklistService checklistService,
            ChecklistItemService checklistItemService) {
        this.applicationContext = applicationContext;
        this.checklistService = checklistService;
        this.checklistItemService = checklistItemService;
    }

    @Override
    public boolean isValid(UserData userData) {
        return true;
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        if (metadata != null && !metadata.isEmpty()) {
            throw new CommandInitializeException(LocalizedText.getExpectedCommandFormat(this));
        }
        this.userData = userData;
        this.commandManager = applicationContext.getBean(CommandManager.class);
    }

    @Override
    public String getCode() {
        return Commands.LIST_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        List<BotCommand> allCommands = commandManager.getAllCommands();
        List<BotCommand> availableCommands = allCommands.stream()
                .filter(botCommand -> !ListCommand.class.equals(botCommand.getClass()))
                .filter(botCommand -> botCommand.isValid(userData))
                .collect(Collectors.toList());
        List<String> answer = new LinkedList<>();

        fillUserState(answer, userData.getState());

        if (availableCommands.isEmpty()) {
            answer.add(LocalizedText.HAS_NOT_AVAILABLE_COMMANDS);
        } else {
            answer.add(LocalizedText.AVAILABLE_COMMANDS);
            answer.add("");
            availableCommands.stream()
                    .map(command -> String.format(LocalizedText.FORMAT_TEMPLATE, LocalizedText.getCommandFormat(command)))
                    .forEach(answer::add);
        }

        return new BotResponse(userData.getId(), answer);
    }

    private void fillUserState(List<String> answer, UserState state) {
        if (state.getChecklistId() != null) {
            Checklist checklist = checklistService.find(state.getChecklistId());
            answer.add(String.format(LocalizedText.CHECKLIST_TEMPLATE, checklist.getTitle()));
        }
        if (state.getChecklistItemId() != null) {
            ChecklistItem checklistItem = checklistItemService.find(state.getChecklistItemId());
            answer.add(String.format(LocalizedText.CHECKLIST_ITEM_TEMPLATE, checklistItem.getCapture()));
        }
    }
}
