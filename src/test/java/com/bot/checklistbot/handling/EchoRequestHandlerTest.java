package com.bot.checklistbot.handling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.bot.BotResponse;
import com.bot.checklistbot.handling.implementations.EchoRequestHandler;

class EchoRequestHandlerTest {
    private EchoRequestHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new EchoRequestHandler();
    }

    @Test
    public void handle_correctEchoResponse() {
        long userId = 0L;
        String messageText = "message";
        BotMessage message = new BotMessage(userId, messageText);

        BotResponse response = handler.handle(message);

        assertEquals(userId, response.getUserId());
        assertIterableEquals(Collections.singletonList(messageText), response.getResponse());
    }

    @Test
    public void handle_messageIsNull() {
        long userId = 0L;
        BotMessage message = new BotMessage(userId, null);

        BotResponse response = handler.handle(message);

        assertEquals(userId, response.getUserId());
        Assertions.assertIterableEquals(Collections.emptyList(), response.getResponse());
    }
}