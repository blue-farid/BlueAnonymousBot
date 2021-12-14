package telegram.command;

import telegram.BlueAnonymousBot;

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
    public void execute() {
        if (optionalCommand.isEmpty())
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);

    }

}
