package telegram.command;

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

    protected final String message;
    protected final Optional<String> optionalCommand;

    public Command() {
        this.message = null;
        optionalCommand = Optional.empty();
    }
    public Command(String message) {
        this.message = message;
        this.optionalCommand = Optional.empty();
    }

    public Command(String message, String optionalCommand) {
        this.message = message;
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

    public static Command valueOf(String value) {
        String[] values = value.split(" ");
        String caseValue = values[0];
        switch (caseValue) {
            case START:
                try {
                    return new StartCommand(values[1]);
                } catch (IndexOutOfBoundsException e) {
                    return new StartCommand();
                }
            case RESTART:
                return new RestartCommand();
            case ANONYMOUS_LINK:
                return new AnonymousLinkCommand();
            case ANONYMOUS_CONNECTION:
                return new AnonymousConnectionCommand();
            case ANONYMOUS_TO_GROUP:
                return new AnonymousToGroupCommand();
            case SPECIFIC_CONNECTION:
                return new SpecificConnectionCommand();
            case SCORE:
                return new ScoreCommand();
            case HELP:
                return new HelpCommand();
            default:
                throw new IllegalArgumentException();
        }
    }

    public String getMessage() {
        return message;
    }

    public abstract String execute();
}
