package com.bot.checklistbot.handling.implementations;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.RequestHandler;

/**
 * Простейшая имплементация класса {@link RequestHandler}
 */
@Component
public class EchoRequestHandler implements RequestHandler {
    @Override
    public BotResponse handle(BotMessage message) {
        List<String> result = message.getMessage() == null
                ? Collections.emptyList()
                : Collections.singletonList(message.getMessage());
        return new BotResponse(message.getUserId(), result);
    }
}
