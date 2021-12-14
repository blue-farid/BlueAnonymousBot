package telegram.command;

import telegram.BlueAnonymousBot;

public class StartCommand extends Command {
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public StartCommand(String chatId) {
        super(chatId);
    }

    public StartCommand(String chatId, String optionalCommand) {
        super(chatId, optionalCommand);
    }

    @Override
    public void execute() {
        if (optionalCommand.isEmpty()) {
            this.sendMessage.setText(localMessage);
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
            return;
        }

    }

}
