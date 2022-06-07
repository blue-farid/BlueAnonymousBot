package telegram.command;

import com.google.common.base.Strings;
import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import properties.Commands;
import telegram.BlueAnonymousBot;
import telegram.command.help.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Admin {
}

public abstract class Command {
    protected final Optional<String> optionalCommand;
    protected SendMessage sendMessage;
    protected Client client;
    protected Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public Command(Client client) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(client.getChatId()));
        optionalCommand = Optional.empty();
        this.client = client;
    }

    public Command(Client client, String optionalCommand) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(client.getChatId()));
        this.optionalCommand = Optional.of(optionalCommand);
        this.client = client;
    }

    public static Command valueOf(Update update) {
        Message message = null;
        String caseValue;
        Client client;
        String[] callBackValues = null;

        if (update.hasCallbackQuery()) {
            client = ClientDao.getInstance().searchById(
                    update.getCallbackQuery().getFrom().getId());
            callBackValues = update.getCallbackQuery().getData().split(" ");
            caseValue = callBackValues[0];
        } else if (update.hasMessage()) {
            message = update.getMessage();
            caseValue = message.getText();
            client = ClientDao.getInstance().searchById(
                    message.getFrom().getId());
        } else {
            throw new IllegalArgumentException();
        }
        if (client.getClientState() == ClientState.NORMAL ||
                (update.getMessage() != null && update.getMessage().getText() != null &&
                        update.getMessage().getText().equals(Commands.CANCEL.get()))) {
            if (caseValue.contains(Commands.START.get())) {
                String[] values = caseValue.split(" ");
                if (values[0].equals(Commands.START.get()) && values.length > 1) {
                    return new StartCommand(client, values[1]);
                } else {
                    return new StartCommand(client);
                }
            } else if (caseValue.equals(Commands.RESTART.get())) {
                return new RestartCommand(client);
            } else if (caseValue.equals(Commands.HELP_ANONYMOUS_TO_GROUP.get())) {
                return new HelpAnonymousToGroupCommand(client);
            } else if (caseValue.equals(Commands.HELP_FREE_VIP.get())) {
                return new HelpFreeVIPCommand(client);
            } else if (caseValue.equals(Commands.HELP_RANDOM_ANONYMOUS.get())) {
                return new HelpRandomAnonymousCommand(client);
            } else if (caseValue.equals(Commands.HELP_RECEIVE_ANONYMOUS_MESSAGE.get())) {
                return new HelpReceiveAnonymousCommand(client);
            } else if (caseValue.equals(Commands.HELP_SPECIFIC_CONNECTION.get())) {
                return new HelpSpecificConnectionCommand(client);
            } else if (caseValue.equals(Commands.HELP_WHAT_FOR.get())) {
                return new HelpWhatForCommand(client);
            } else if (caseValue.equals(Commands.ANONYMOUS_CONNECTION.get())) {
                return new AnonymousConnectionCommand(client);
            } else if (caseValue.equals(Commands.ANONYMOUS_TO_GROUP.get())) {
                return new AnonymousToGroupCommand(client);
            } else if (caseValue.equals(Commands.ANONYMOUS_LINK.get())) {
                return new AnonymousLinkCommand(ClientDao.getInstance().searchById(
                        update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(Commands.SPECIFIC_CONNECTION.get())) {
                return new SpecificConnectionCommand(client);
            } else if (caseValue.equals(Commands.ANONYMOUS_LINK.get()) || caseValue.equals(Commands.LINK.get())) {
                return new AnonymousLinkCommand(ClientDao.getInstance().searchById(
                        update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(Commands.HELP.get())) {
                return new HelpCommand(client);
            } else if (caseValue.equals(Commands.SCORE.get())) {
                return new ScoreCommand(client);
            } else if (caseValue.equals(Commands.CANCEL.get())) {
                return new CancelCommand(client);
            } else if (caseValue.equals(Commands.ANSWER.get())) {
                return new AnswerCommand(client, Long.parseLong(callBackValues[1]),
                        update.getCallbackQuery().getMessage().getMessageId(),
                        Integer.parseInt(callBackValues[2]));
            } else if (caseValue.equals(Commands.BLOCK.get())) {
                return new BlockCommand(Long.parseLong(callBackValues[1]), client);
            } else if (caseValue.equals(Commands.PRINT_ALL_USERS.get())) {
                return new PrintAllUsersCommand(client);
            } else if (caseValue.equals(Commands.GET_DATABASE.get())) {
                return new GetDatabaseCommand(client);
            } else if (caseValue.equals(Commands.ADMIN_CONNECT.get())) {
                return new AdminSpecificConnectionCommand(client);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (client.getClientState() == ClientState.SENDING_MESSAGE_TO_CONTACT) {
            if (callBackValues != null && callBackValues[0].equals(Commands.ANSWER.get())) {
                return new AnswerCommand(client, Integer.parseInt(callBackValues[1]),
                        update.getCallbackQuery().getMessage().getMessageId(),
                        Integer.parseInt(callBackValues[2]));
            }
            return new SendMessageToContact(client,
                    update.getMessage());
        } else if (client.getClientState() == ClientState.SENDING_CONTACT_INFO) {
            return new FindContactCommand(client, message);
        } else if (client.getClientState() == ClientState.CHOOSING_CONTACT_SEX) {
            return new ChooseContactSexCommand(client);
        } else if (client.getClientState() == ClientState.ADMIN_SENDING_CONTACT_ID) {
            return new AdminFindContactCommand(client, message);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void execute() throws IllegalAccessException {
        if (getClass().isAnnotationPresent(Admin.class)) {
            if (!client.isAdmin()) {
                addBaseLog();
                throw new IllegalAccessException("The Client is not Admin!");
            }
        }
    }

    protected void addBaseLog() {
        logger.info(client.getClientInfo());
        MDC.clear();
    }

    public String getChatId() {
        return String.valueOf(client.getChatId());
    }

    protected void executeMessages(List<String> strings) {
        for (String string : strings) {
            if (!Strings.isNullOrEmpty(string)) {
                sendMessage.setText(string);
                BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            }
        }
    }
}