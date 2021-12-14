package telegram.command;

import model.Client;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public abstract class Command {
    private static final String START = "/start";
    private static final String RESTART = "/restart";
    private static final String ANONYMOUS_CONNECTION = "\uD83D\uDD17 به یه ناشناس وصلم کن!";
    private static final String SPECIFIC_CONNECTION = "\uD83D\uDC8C به مخاطب خاصم وصلم کن!";
    private static final String ANONYMOUS_LINK = "لینک ناشناس من\uD83D\uDCEC";
    private static final String ANONYMOUS_TO_GROUP = "\uD83D\uDC65 پیام ناشناس به گروه";
    private static final String HELP = "راهنما";
    private static final String SCORE = "\uD83C\uDFC6 افزایش امتیاز";

    protected SendMessage sendMessage;
    protected final Optional<String> optionalCommand;

    public Command() {
        optionalCommand = Optional.empty();
    }
    public Command(String message) {
        this.sendMessage = new SendMessage();
        this.sendMessage.setText(message);
        this.optionalCommand = Optional.empty();
    }

    public Command(String message, String optionalCommand) {
        this.sendMessage = new SendMessage();
        this.sendMessage.setText(message);
        this.optionalCommand = Optional.of(optionalCommand);
    }

    public static String getSTART() {
        return START;
    }

    public static String getRESTART() {
        return RESTART;
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

    public static Command valueOf(Update update) {
        String caseValue = update.getMessage().getText();

        if (caseValue.contains(START)) {
            String[] values = caseValue.split(" ");
            if (values[0].equals(START) && values.length > 1) {
                return new StartCommand(values[1]);
            } else {
                return new StartCommand();
            }
        } else if (caseValue.equals(RESTART)) {
            return new RestartCommand();
        } else if (caseValue.equals(ANONYMOUS_CONNECTION)) {
            return new AnonymousConnectionCommand();
        } else if (caseValue.equals(ANONYMOUS_TO_GROUP)) {
            return new AnonymousToGroupCommand();
        } else if (caseValue.equals(ANONYMOUS_LINK)) {
            return new AnonymousLinkCommand(new Client(update.getMessage().getFrom()));
        } else if (caseValue.equals(SPECIFIC_CONNECTION)) {
            return new SpecificConnectionCommand();
        } else if (caseValue.equals(HELP)) {
            return new HelpCommand();
        } else if (caseValue.equals(SCORE)) {
            return new ScoreCommand();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public abstract SendMessage execute();
}
