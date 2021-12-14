package telegram.command;

public class RestartCommand extends Command {
    private static final String message = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public RestartCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
    }
}
