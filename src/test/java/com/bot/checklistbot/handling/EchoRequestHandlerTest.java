package com.bot.checklistbot.handling;

import com.bot.checklistbot.bot.BotMessage;
import com.bot.checklistbot.bot.BotResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertArrayEquals(new String[] {messageText}, response.getResponse());
    }

    @Test
    public void handle_messageIsNull() {
        long userId = 0L;
        BotMessage message = new BotMessage(userId, null);

        BotResponse response = handler.handle(message);

        assertEquals(userId, response.getUserId());
        assertArrayEquals(new String[0], response.getResponse());
    }
}