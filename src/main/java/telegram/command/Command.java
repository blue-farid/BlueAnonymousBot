package telegram.command;

import com.google.common.base.Strings;
import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import properties.Commands;
import telegram.BlueAnonymousBot;
import telegram.command.help.*;

import java.lang.annotation.*;
import java.util.List;
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

    public static Command valueOf(Update update) throws Exception {
        Message message = null;
        String caseValue;
        String chatId;
        Client client;
        String[] callBackValues = null;

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
                (update.getMessage() != null && update.getMessage().getText() != null &&
                        update.getMessage().getText().equals(Commands.CANCEL.get()))) {
            if (caseValue.contains(Commands.START.get())) {
                String[] values = caseValue.split(" ");
                if (values[0].equals(Commands.START.get()) && values.length > 1) {
                    return new StartCommand(chatId, values[1], client);
                } else {
                    return new StartCommand(chatId);
                }
            } else if (caseValue.equals(Commands.RESTART.get())) {
                return new RestartCommand(chatId);
            } else if (caseValue.equals(Commands.HELP_ANONYMOUS_TO_GROUP.get())){
                return new HelpAnonymousToGroupCommand(chatId);
            }else if (caseValue.equals(Commands.HELP_FREE_VIP.get())){
                return new HelpFreeVIPCommand(chatId);
            }else if (caseValue.equals(Commands.HELP_RANDOM_ANONYMOUS.get())){
                return new HelpRandomAnonymousCommand(chatId);
            }else if (caseValue.equals(Commands.HELP_RECEIVE_ANONYMOUS_MESSAGE.get())){
                return new HelpReceiveAnonymousCommand(chatId);
            }else if (caseValue.equals(Commands.HELP_SPECIFIC_CONNECTION.get())){
                return new HelpSpecificConnectionCommand(chatId);
            }else if (caseValue.equals(Commands.HELP_WHAT_FOR.get())){
                return new HelpWhatForCommand(chatId);
            } else if (caseValue.equals(Commands.ANONYMOUS_CONNECTION.get())) {
                return new AnonymousConnectionCommand(chatId, client);
            } else if (caseValue.equals(Commands.ANONYMOUS_TO_GROUP.get())) {
                return new AnonymousToGroupCommand(chatId);
            } else if (caseValue.equals(Commands.ANONYMOUS_LINK.get())) {
                return new AnonymousLinkCommand(chatId,
                        ClientDao.getInstance().searchById(
                                update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(Commands.SPECIFIC_CONNECTION.get())) {
                return new SpecificConnectionCommand(chatId, client);
            } else if (caseValue.equals(Commands.ANONYMOUS_LINK.get()) || caseValue.equals(Commands.LINK.get())) {
                return new AnonymousLinkCommand(chatId,
                        ClientDao.getInstance().searchById(
                                update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(Commands.HELP.get())) {
                return new HelpCommand(chatId);
            } else if (caseValue.equals(Commands.SCORE.get())) {
                return new ScoreCommand(chatId);
            } else if (caseValue.equals(Commands.CANCEL.get())) {
                return new CancelCommand(chatId,client);
            } else if (caseValue.equals(Commands.ANSWER.get())){
                return new AnswerCommand(chatId, client, Long.parseLong(callBackValues[1]),
                        update.getCallbackQuery().getMessage().getMessageId(),
                        Integer.parseInt(callBackValues[2]));
            }else if (caseValue.equals(Commands.BLOCK.get())) {
                return new BlockCommand(chatId, Long.parseLong(callBackValues[1]), client);
            } else if (client.isAdmin() && caseValue.equals(Commands.PRINT_ALL_USERS.get())) {
                return new PrintAllUsersCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals(Commands.GET_DATABASE.get())) {
                return new GetDatabaseCommand(chatId);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (client.getClientState() == ClientState.SENDING_MESSAGE_TO_CONTACT) {
            if (callBackValues != null && callBackValues[0].equals(Commands.ANSWER.get())) {
                return new AnswerCommand(chatId, client, Integer.parseInt(callBackValues[1]),
                        update.getCallbackQuery().getMessage().getMessageId(),
                        Integer.parseInt(callBackValues[2]));
            }
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

    public String getChatId() {
        return chatId;
    }

    protected void executeMessages(List<String> strings) {
        for (String string: strings) {
            if (!Strings.isNullOrEmpty(string)) {
                sendMessage.setText(string);
                BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            }
        }
    }
}

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@interface Admin {
}