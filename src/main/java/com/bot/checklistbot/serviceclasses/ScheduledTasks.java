package com.bot.checklistbot.serviceclasses;

import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.bot.TelegramBot;
import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistItem;
import com.bot.checklistbot.model.checklists.ChecklistItemService;
import com.bot.checklistbot.model.userstate.UserData;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import static com.bot.checklistbot.serviceclasses.Constants.Schedule.TIME_ZONE;

@Service
@EnableScheduling
public class ScheduledTasks {

    private final ChecklistItemService service;
    private final CronParser parser;
    private final TelegramBot telegramBot;

    @Autowired
    public ScheduledTasks(ChecklistItemService service, TelegramBot telegramBot)
    {
        this.service = service;
        this.telegramBot = telegramBot;
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX);
        parser = new CronParser(cronDefinition);
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void activateChecklistItems() {
        for (ChecklistItem checklistItem: service.getAll()) {
            if (!checklistItem.hasSchedule())
                continue;

            Cron cron = parser.parse(checklistItem.getSchedule());
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(TIME_ZONE));
            ExecutionTime executionTime = ExecutionTime.forCron(cron);

            if (!executionTime.isMatch(now))
                continue;

            checklistItem.setState(true);
            service.save(checklistItem);
            telegramBot.sendNotification(createNotification(checklistItem));
        }
    }

    private BotResponse createNotification(ChecklistItem checklistItem) {
        Checklist checklist = checklistItem.getChecklist();
        UserData userData = checklist.getOwner();

        return new BotResponse(
                userData.getId(),
                Collections.singletonList(LocalizedText.notifyItemActive(checklistItem.getCapture())),
                true);
    }
}
