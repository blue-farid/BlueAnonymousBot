package telegram.command;

import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.*;
import java.util.Optional;

public abstract class Command {
    private static final String START = "/start";
    private static final String ANSWER = "answer";
    private static final String BLOCK = "block";
    private static final String CANCEL = "انصراف";
    private static final String RESTART = "/restart";
    private static final String ANONYMOUS_CONNECTION = "\uD83D\uDD17 به یه ناشناس وصلم کن!";
    private static final String SPECIFIC_CONNECTION = "\uD83D\uDC8C به مخاطب خاصم وصلم کن!";
    private static final String ANONYMOUS_LINK = "لینک ناشناس من\uD83D\uDCEC";
    private static final String ANONYMOUS_TO_GROUP = "\uD83D\uDC65 پیام ناشناس به گروه";
    private static final String HELP = "راهنما";
    private static final String SCORE = "\uD83C\uDFC6 افزایش امتیاز";
    private static final String PRINT_ALL_USERS = "printAllUsers";
    private static final String GET_DATABASE = "getDatabase";
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

    public static String getSTART() {
        return START;
    }

    public static String getRESTART() {
        return RESTART;
    }

    public static String getANSWER() {
        return ANSWER;
    }

    public static String getAnonymousConnection() {
        return ANONYMOUS_CONNECTION;
    }


    public static String getSpecificConnection() {
        return SPECIFIC_CONNECTION;
    }

    public static String getAnonymousLink() {
        return ANONYMOUS_LINK;
    }

    public static String getAnonymousToGroup() {
        return ANONYMOUS_TO_GROUP;
    }

    public static String getHELP() {
        return HELP;
    }

    public static String getSCORE() {
        return SCORE;
    }

    public static String getCANCEL() {
        return CANCEL;
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
        if (client.getClientState() == ClientState.NORMAL || update.getMessage().getText().equals("انصراف")) {
            if (caseValue.contains(START)) {
                String[] values = caseValue.split(" ");
                if (values[0].equals(START) && values.length > 1) {
                    return new StartCommand(chatId, values[1], client);
                } else {
                    return new StartCommand(chatId);
                }
            } else if (caseValue.equals(RESTART)) {
                return new RestartCommand(chatId);
            } else if (caseValue.equals(ANONYMOUS_CONNECTION)) {
                return new AnonymousConnectionCommand(chatId);
            } else if (caseValue.equals(ANONYMOUS_TO_GROUP)) {
                return new AnonymousToGroupCommand(chatId);
            } else if (caseValue.equals(ANONYMOUS_LINK) || caseValue.equals("/link")) {
                return new AnonymousLinkCommand(chatId,
                        ClientDao.getInstance().searchById(
                                update.getMessage().getFrom().getId()));
            } else if (caseValue.equals(SPECIFIC_CONNECTION)) {
                return new SpecificConnectionCommand(chatId, client);
            } else if (caseValue.equals(HELP)) {
                return new HelpCommand(chatId);
            } else if (caseValue.equals(SCORE)) {
                return new ScoreCommand(chatId);
            } else if (caseValue.equals(CANCEL)) {
                return new CancelCommand(chatId, client);
            } else if (caseValue.equals(ANSWER)) {
                return new AnswerCommand(chatId, client, callBackValues[1]);
            } else if (caseValue.equals(BLOCK)) {
                return new BlockCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals(PRINT_ALL_USERS)) {
                return new PrintAllUsersCommand(chatId);
            } else if (client.isAdmin() && caseValue.equals(GET_DATABASE)) {
                return new GetDatabaseCommand(chatId);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (client.getClientState() == ClientState.SENDING_MESSAGE_WITH_DEEPLINK) {
            return new SendMessageWithDeepLinkCommand(chatId, client,
                    update.getMessage().getText());
        } else if (client.getClientState() == ClientState.SENDING_CONTACT_INFO) {
            return new FindContactCommand(chatId, client, message);
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
