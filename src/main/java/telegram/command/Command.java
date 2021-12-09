package telegram.command;

public abstract class Command {
    private static final String START = "/start";
    private static final String RESTART = "/restart";
    private static final String ANONYMOUS_CONNECTION = "\uD83D\uDD17 به یه ناشناس وصلم کن!";
    private static final String SPECIFIC_CONNECTION = "\uD83D\uDC8C به مخاطب خاصم وصلم کن!";
    private static final String ANONYMOUS_LINK = "لینک ناشناس من\uD83D\uDCEC";
    private static final String ANONYMOUS_TO_GROUP = "\uD83D\uDC65 پیام ناشناس به گروه";
    private static final String HELP = "راهنما";
    private static final String SCORE = "\uD83C\uDFC6 افزایش امتیاز";

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
        return switch (value) {
            case START -> new StartCommand();
            case RESTART -> new RestartCommand();
            case ANONYMOUS_CONNECTION -> new AnonymousConnectionCommand();
            case SPECIFIC_CONNECTION -> new SpecificConnectionCommand();
            case ANONYMOUS_LINK -> new AnonymousLinkCommand();
            case ANONYMOUS_TO_GROUP -> new AnonymousToGroupCommand();
            case HELP -> new HelpCommand();
            case SCORE -> new ScoreCommand();
            default -> throw new IllegalArgumentException();
        };
    }

    public abstract String execute();
}
