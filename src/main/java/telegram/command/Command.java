package telegram.command;

import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.BlueAnonymousBot;

import java.lang.annotation.*;
import java.util.Optional;
import java.util.Properties;

public abstract class Command {
    protected final Optional<String> optionalCommand;
    protected SendMessage sendMessage;

    public Command(String chatId) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        optionalCommand = Optional.empty();
    }

    public Command(String chatId, String optionalCommand) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        this.optionalCommand = Optional.of(optionalCommand);
    }

    public static Command valueOf(Update update)  {
        Message message ;
        String caseValue;
        String chatId;
        Client client;
        String[] callBackValues=new String[2];

        if (update.hasCallbackQuery()) {
             client = ClientDao.getInstance().searchById(
                    update.getCallbackQuery().getFrom().getId());
            chatId=client.getChatId().toString();
            callBackValues=update.getCallbackQuery().getData().split(" ");
            caseValue=callBackValues[0];
        }else  if (update.hasMessage()) {
            message = update.getMessage();
            caseValue = message.getText();
            chatId = message.getChatId().toString();
            client = ClientDao.getInstance().searchById(
                    message.getFrom().getId());
        } else {
            throw new IllegalArgumentException();
        }

        if (client.getClientState() == ClientState.NORMAL||
                update.getMessage().getText().equals("command.cancel")) {
            if (caseValue.contains("command.start")) {
                String[] values = caseValue.split(" ");
                if (values[0].equals("command.start") && values.length > 1) {
                    return new StartCommand(chatId, values[1], client);
                } else {
                    return new StartCommand(chatId);
                }
            } else if (caseValue.equals("command.restart")) {
                return new RestartCommand(chatId);
            } else if (caseValue.equals("command.anonymous_connection")) {
                return new AnonymousConnectionCommand(chatId);
            } else if (caseValue.equals("command.anonymous_to_group")) {
                return new AnonymousToGroupCommand(chatId);
            } else if (caseValue.equals("command.anonymous_link")) {
                return new AnonymousLinkCommand(chatId,
                        ClientDao.getInstance().searchById(
                                update.getMessage().getFrom().getId()));
            } else if (caseValue.equals("command.specific_connection")) {
                return new SpecificConnectionCommand(chatId);
            } else if (caseValue.equals("command.help")) {
                return new HelpCommand(chatId);
            } else if (caseValue.equals("command.score")) {
                return new ScoreCommand(chatId);
            } else if (caseValue.equals("command.cancel")) {
                return new CancelCommand(chatId,client);
            } else if (caseValue.equals("command.answer")){
                return new AnswerCommand(chatId,client,callBackValues[1]);
            }else if (caseValue.equals("command.block")){
                return new BlockCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals("command.print_all_users")) {
                return new PrintAllUsersCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals("command.get_database")) {
                return new GetDatabaseCommand(chatId);
            } else {
                throw new IllegalArgumentException();
            }
        }
        else if (client.getClientState() == ClientState.SENDING_MESSAGE_WITH_DEEPLINK) {
            return new SendMessageWithDeepLinkCommand(chatId, client,
                    update.getMessage().getText());
        } else {
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
