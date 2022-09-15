package com.blue_farid.blue_anonymous_bot.telegram;

import com.blue_farid.blue_anonymous_bot.exception.ConfigException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@PropertySource("classpath:bot_config.properties")
@Component
@RequiredArgsConstructor
public class BlueAnonymousBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    private final Environment environment;

    @PostConstruct
    private void init() {
        boolean isTest = Boolean.parseBoolean(environment.getProperty("is.test"));
        String testBotUsername = environment.getProperty("test.bot.username");
        String testBotToken = environment.getProperty("test.bot.token");

        if (isTest) {
            if (Strings.isEmpty(testBotToken) || Strings.isEmpty(testBotUsername))
                throw new ConfigException("'is.test' flag is true but test configs are empty!");
            this.botUsername = testBotUsername;
            this.botToken = testBotToken;
        }
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
