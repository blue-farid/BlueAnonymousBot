package telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RestartCommand extends Command {
    private static final String message = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public RestartCommand() {
        super(message);
    }

    @Override
    public SendMessage execute() {
        return this.sendMessage;
    }
}
