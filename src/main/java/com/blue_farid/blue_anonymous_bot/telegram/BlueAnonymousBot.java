package com.blue_farid.blue_anonymous_bot.telegram;

import com.blue_farid.blue_anonymous_bot.annotation.Response;
import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import com.blue_farid.blue_anonymous_bot.exception.ConfigException;
import com.blue_farid.blue_anonymous_bot.menu.CancelMenu;
import com.blue_farid.blue_anonymous_bot.menu.MainMenu;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import com.blue_farid.blue_anonymous_bot.telegram.command.CommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class BlueAnonymousBot extends TelegramLongPollingBot {
    private final Environment environment;
    private final ClientService clientService;
    @Getter
    private final MainMenu mainMenu;
    @Getter
    private final CancelMenu cancelMenu;
    @Lazy
    @Autowired
    private CommandService commandService;
    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

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
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Long id = update.getMessage().getChatId();
        String caseValue;
        Message message = null;
        if (update.hasMessage()) {
            if (!clientService.exists(id)) {
                clientService.addClient(new Client(update.getMessage().getFrom()));
            } else {
                clientService.updateClient(new Client(update.getMessage().getFrom()));
            }
            caseValue = update.getMessage().getText();
            message = update.getMessage();
        } else {
            caseValue = update.getCallbackQuery().getData();
        }

        Client client = clientService.getClientById(id);
        for (Method method : CommandService.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Response.class)) {
                Response response = method.getAnnotation(Response.class);
                if ((Strings.isEmpty(response.value()) || response.value().equals(caseValue)) &&
                        response.acceptedState().equals(client.getClientState())) {
                    method.invoke(this.commandService, new RequestDto(client, message));
                    break;
                }
            }
        }
    }

    public int executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
            return 0;
        } catch (TelegramApiRequestException e) {
            return e.getErrorCode();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
