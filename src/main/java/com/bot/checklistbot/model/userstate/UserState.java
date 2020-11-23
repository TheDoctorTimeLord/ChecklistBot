package com.bot.checklistbot.model.userstate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Состояние, в котором пользователь оказался после совершения последнего действия в системе. Необходимо для
 * запоминания контекста выполнения и организации логики обработки действий пользователя
 */
public class UserState {
    private static final Logger LOG = LoggerFactory.getLogger(UserState.class);
    private static final Pattern FROM_STR = Pattern.compile("UserState\\[checklistId=(?<checklistId>[0-9a-z]+), "
            + "checklistItemId=(?<checklistItemId>[0-9a-z]+)]");

    private Long checklistId;
    private Long checklistItemId;

    public UserState() {}

    public UserState(Long checklistId, Long checklistItemId) {
        if (checklistId == null && checklistItemId != null) {
            throw new IllegalArgumentException("ChecklistId is null, but checklistItemId is not null");
        }

        this.checklistId = checklistId;
        this.checklistItemId = checklistItemId;
    }

    public Long getChecklistId()
    {
        return checklistId;
    }

    public Long getChecklistItemId()
    {
        return checklistItemId;
    }

    @Override
    public String toString()
    {
        return "UserState[checklistId=" + checklistId + ", checklistItemId=" + checklistItemId + "]";
    }

    public static UserState createByString(String stringPresentation) {
        Matcher matcher = FROM_STR.matcher(stringPresentation);
        if (!matcher.find()) {
            LOG.error("Receive incorrect presentation of UserState: " + stringPresentation);
            return new UserState();
        }
        Long checklistId = parseLong(matcher.group("checklistId"));
        Long checklistItemId = parseLong(matcher.group("checklistItemId"));

        return new UserState(checklistId, checklistItemId);
    }

    private static Long parseLong(String value) {
        return value.equals("null") ? null : Long.parseLong(value);
    }
}
