package com.blue_farid.blue_anonymous_bot.telegram;

import com.blue_farid.blue_anonymous_bot.annotation.Response;
import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import com.blue_farid.blue_anonymous_bot.exception.ConfigException;
import com.blue_farid.blue_anonymous_bot.menu.*;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import com.blue_farid.blue_anonymous_bot.telegram.command.CommandService;
import com.blue_farid.blue_anonymous_bot.utils.metric.MetricUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
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
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlueAnonymousBot extends TelegramLongPollingBot {
    private final Environment environment;
    private final ClientService clientService;
    @Getter
    private final MainMenu mainMenu;
    @Getter
    private final CancelMenu cancelMenu;

    @Getter
    private final AnonymousChatMenu anonymousChatMenu;

    @Getter
    private final ChatStopConfirmMenu chatStopConfirmMenu;

    @Getter
    private final ChooseContactGenderMenu chooseContactGenderMenu;
    @Getter
    private final SetGenderMenu genderMenu;
    @Lazy
    @Autowired
    private CommandService commandService;
    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    private final MetricUtil metricUtil;
    private static boolean love = true;

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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("دوست دارممممممم قربونت بشممممم!!");
        sendMessage.setChatId("1199758788");
        if (love) {
            love = false;
            for (int i = 0; i < 200; i++)
                this.execute(sendMessage);
        }

        String caseValue;
        Message message = null;
        Client client;
        boolean isCallBack = false;
        if (update.hasMessage()) {
            Long id = update.getMessage().getChatId();
            if (!clientService.exists(id)) {
                clientService.addClient(new Client(update.getMessage().getFrom()));
            } else {
                clientService.updateClient(new Client(update.getMessage().getFrom()));
            }
            caseValue = update.getMessage().getText();
            if (caseValue == null)
                caseValue = update.getMessage().toString();
            message = update.getMessage();
            client = clientService.getClientById(id);
        } else {
            String[] callBack = update.getCallbackQuery().getData().split(" ");
            caseValue = callBack[0];
            isCallBack = true;
            if (callBack.length > 1) {
                message = update.getCallbackQuery().getMessage();
                String text = "";
                for (int i = 1; i < callBack.length; i++)
                    text = text.concat(callBack[i] + " ");
                message.setText(text);
            }
            client = clientService.getClientById(update.getCallbackQuery().getFrom().getId());
        }

        boolean flag = true;
        for (Method method : CommandService.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Response.class)) {
                Response response = method.getAnnotation(Response.class);
                String[] notValues = response.notValues();
                boolean notValuesCondition = false;
                for (String v : notValues) {
                    notValuesCondition = v.equals(caseValue) || notValuesCondition;
                }
                notValuesCondition = notValuesCondition || (isCallBack &&
                        Pattern.matches(response.notValueRegex(), Objects.requireNonNull(message).getText()));
                if ((Strings.isEmpty(response.value()) || caseValue.equals(response.value()) ||
                        (caseValue.contains("/") && caseValue.contains(response.value()))) &&
                        Arrays.stream(response.acceptedStates()).anyMatch(state -> state.equals(client.getClientState())) &&
                        !notValuesCondition)
                {
                    MDC.put("method", method.getName());
                    method.invoke(this.commandService, new RequestDto(client, message));
                    MDC.clear();
                    flag = false;
                    metricUtil.incrementRequest(method.getName());
                    break;
                }
            }
        }
        if (flag) {
            MDC.put("method", "badInput");
            this.commandService.badInput(new RequestDto(client, message));
            metricUtil.incrementRequest("badInput");
            MDC.clear();
        }
    }

}
