package com.bot.checklistbot.handling.commands.checklist;

import static com.bot.checklistbot.serviceclasses.LocalizedText.CHECKLIST_ITEM_MARKER;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.bot.checklistbot.handling.BotCommand;
import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.Command;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistService;
import com.bot.checklistbot.model.userstate.UserData;
import com.bot.checklistbot.model.userstate.UserDataService;
import com.bot.checklistbot.model.userstate.UserState;
import com.bot.checklistbot.serviceclasses.CommandInitializeException;
import com.bot.checklistbot.serviceclasses.Constants;
import com.bot.checklistbot.serviceclasses.LocalizedText;
import com.bot.checklistbot.serviceclasses.StringUtils;

/**
 * Команда для показа определённого списка пользователю
 */
@Command
public class ViewChecklistCommand implements BotCommand {
    private final ChecklistService checklistService;
    private final UserDataService userService;
    private Checklist checklist;
    private UserData userData;

    @Autowired
    public ViewChecklistCommand(ChecklistService checklistService, UserDataService userService) {
        this.checklistService = checklistService;
        this.userService = userService;
    }

    @Override
    public boolean isValid(UserData userData) {
        return true;
    }

    @Override
    public void initialize(UserData userData, String metadata) throws CommandInitializeException {
        this.userData = userData;
        this.checklist = checklistService.findByTitle(metadata);

        if (checklist == null && userData.getState().getChecklistId() != null) {
            checklist = checklistService.find(userData.getState().getChecklistId());
        }

        if (checklist == null || userData.getId() != checklist.getOwner().getId()) {
            throw new CommandInitializeException(LocalizedText.CHECKLIST_NOT_FOUND);
        }
    }

    @Override
    public String getCode() {
        return Constants.Commands.VIEW_CHECKLIST_COMMAND_CODE;
    }

    @Override
    public BotResponse execute() {
        userService.changeUserState(userData, new UserState(checklist.getId(), null));

        List<String> answer = new LinkedList<>();
        answer.add(LocalizedText.titleChecklist(checklist.getTitle()));
        if (!StringUtils.isEmpty(checklist.getCapture())) {
            answer.add(checklist.getCapture());
        }
        answer.add("");

        boolean enableHtml = true;

        if(checklist.getItems().isEmpty()) {
            answer.add(LocalizedText.EMPTY_ITEMS);
            enableHtml = false;
        } else {
            answer.addAll(checklist.getItems().stream()
                    .map(item -> CHECKLIST_ITEM_MARKER + (item.isState()
                            ? item.getCapture()
                            : StringUtils.strikeOut(item.getCapture())))
                    .collect(Collectors.toList()));
        }
        return new BotResponse(userData.getId(), answer, enableHtml);
    }
}
