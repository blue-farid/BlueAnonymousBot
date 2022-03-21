package telegram.command;

import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import properties.Property;
import telegram.BlueAnonymousBot;

import java.lang.annotation.*;
import java.util.Optional;

public abstract class Command {
    protected final Optional<String> optionalCommand;
    protected SendMessage sendMessage;
    protected String chatId;

    public Command(String chatId) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        optionalCommand = Optional.empty();
        this.chatId=chatId;
    }

    public Command(String chatId, String optionalCommand) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        this.optionalCommand = Optional.of(optionalCommand);
    }
  
    public static Command valueOf(Update update) {
        Message message = null;
        String caseValue;
        String chatId;
        Client client;
        String[] callBackValues = new String[2];

        if (update.hasCallbackQuery()) {
            client = ClientDao.getInstance().searchById(
                    update.getCallbackQuery().getFrom().getId());
            chatId=client.getChatId().toString();
            callBackValues=update.getCallbackQuery().getData().split(" ");
            caseValue=callBackValues[0];
        } else if (update.hasMessage()) {
            message = update.getMessage();
            caseValue = message.getText();
            chatId = message.getChatId().toString();
            client = ClientDao.getInstance().searchById(
                    message.getFrom().getId());
        } else {
            throw new IllegalArgumentException();
        }
        if (client.getClientState() == ClientState.NORMAL||
                (update.getMessage() != null && update.getMessage().getText().equals(Property.COMMANDS_P.get("command.cancel")))) {
            if (caseValue.contains(Property.COMMANDS_P.get("start"))) {
                String[] values = caseValue.split(" ");
                if (values[0].equals(Property.COMMANDS_P.get("start")) && values.length > 1) {
                    return new StartCommand(chatId, values[1], client);
                } else {
                    return new StartCommand(chatId);
                }
            } else if (caseValue.equals(Property.COMMANDS_P.get("restart"))) {
                return new RestartCommand(chatId);
            } else if (caseValue.equals(Property.COMMANDS_P.get("anonymous_connection"))) {
                return new AnonymousConnectionCommand(chatId, client);
            } else if (caseValue.equals(Property.COMMANDS_P.get("anonymous_to_group"))) {
                return new AnonymousToGroupCommand(chatId);
            } else if (caseValue.equals(Property.COMMANDS_P.get("anonymous_link"))) {
                return new AnonymousLinkCommand(chatId,
                        ClientDao.getInstance().searchById(
                                update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(Property.COMMANDS_P.get("specific_connection"))) {
                return new SpecificConnectionCommand(chatId, client);
            } else if (caseValue.equals(Property.COMMANDS_P.get("anonymous_link")) || caseValue.equals(Property.COMMANDS_P.get("command.link"))) {
                return new AnonymousLinkCommand(chatId,
                        ClientDao.getInstance().searchById(
                                update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(Property.COMMANDS_P.get("help"))) {
                return new HelpCommand(chatId);
            } else if (caseValue.equals(Property.COMMANDS_P.get("score"))) {
                return new ScoreCommand(chatId);
            } else if (caseValue.equals(Property.COMMANDS_P.get("cancel"))) {
                return new CancelCommand(chatId,client);
            } else if (caseValue.equals(Property.COMMANDS_P.get("answer"))){
                return new AnswerCommand(chatId,client,callBackValues[1]);
            }else if (caseValue.equals(Property.COMMANDS_P.get("block"))) {
                return new BlockCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals(Property.COMMANDS_P.get("print_all_users"))) {
                return new PrintAllUsersCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals(Property.COMMANDS_P.get("get_database"))) {
                return new GetDatabaseCommand(chatId);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (client.getClientState() == ClientState.SENDING_MESSAGE_WITH_DEEPLINK) {
            return new SendMessageToContact(chatId, client,
                    update.getMessage());
        } else if (client.getClientState() == ClientState.SENDING_CONTACT_INFO) {
            return new FindContactCommand(chatId, client, message);
        } else if (client.getClientState() == ClientState.CHOOSING_CONTACT_SEX) {
            return new ChooseContactSexCommand(chatId);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public abstract void execute();
}

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@interface Admin {
}
