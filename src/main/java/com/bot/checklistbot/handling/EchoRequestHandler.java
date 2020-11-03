package com.bot.checklistbot.handling;

import org.springframework.stereotype.Component;

import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.bot.RequestHandler;

/**
 * Простейшая имплементация класса {@link RequestHandler}
 */
@Component
public class EchoRequestHandler implements RequestHandler {
    @Override
    public BotResponse handle(BotMessage message) {
        String[] result = message.getMessage() == null
                ? new String[0]
                : new String[] {message.getMessage()};
        return new BotResponse(message.getUserId(), result);
    }
}
