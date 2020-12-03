package com.bot.checklistbot.bot;

import com.bot.checklistbot.handling.RequestHandler;
import org.apache.http.client.config.RequestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

import static com.bot.checklistbot.serviceclasses.Constants.Bot.STRING_DELIMITER;
import static com.bot.checklistbot.serviceclasses.Constants.Bot.TIMEOUT;

/**
 * Класс, описывающий telegram бота. Регулирует его запуск
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBot.class);

    private final String botToken;
    private final String botUsername;
    private final RequestHandler handler;

    @Autowired
    public TelegramBot(
            @Value("${bot.telegram.token}") String botToken,
            @Value("${bot.telegram.username}") String botUsername,
            RequestHandler handler) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.handler = handler;
    }

    static {
        ApiContextInitializer.init();
    }

    @PostConstruct
    public void initialize() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .build();

        getOptions().setRequestConfig(requestConfig);
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            LOG.error("telegram bot does not connect", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        BotMessage message = new BotMessage(update.getMessage().getChatId(), update.getMessage().getText());
        BotResponse response = handler.handle(message);
        SendMessage responseToSend = mapResponse(response);

        try {
            execute(responseToSend);
        } catch (TelegramApiException e) {
            LOG.error("Message not send", e);
        }
    }

    public void sendNotification(BotResponse botResponse) {
        SendMessage responseToSend = mapResponse(botResponse);

        try {
            execute(responseToSend);
        } catch (TelegramApiException e) {
            LOG.error("Message not send", e);
        }
    }

    private static SendMessage mapResponse(BotResponse response) {
        SendMessage result = new SendMessage();
        result.setChatId(response.getUserId());
        result.setText(String.join(STRING_DELIMITER, response.getResponse()));
        result.enableHtml(response.isEnableHtml());

        return result;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
