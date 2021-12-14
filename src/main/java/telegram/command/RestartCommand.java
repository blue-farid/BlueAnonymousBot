package telegram.command;

public class RestartCommand extends Command {
    private final String message = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    @Override
    public String execute() {
        return message;
    }
}
