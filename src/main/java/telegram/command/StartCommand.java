package telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartCommand extends Command {
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public StartCommand() {
        super(localMessage);
    }

    public StartCommand(String optionalCommand) {
        super(localMessage, optionalCommand);
    }

    @Override
    public SendMessage execute() {
        if (optionalCommand.isEmpty())
            return this.sendMessage;
        else {
            return null;
        }
    }

}
