package telegram.command;

public class RestartCommand extends Command {
    private static final String message = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public RestartCommand() {
        super(message);
    }

    @Override
    public void execute() {
    }
}
