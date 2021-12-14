package telegram.command;

import menu.MainMenu;
import telegram.BlueAnonymousBot;

public class RestartCommand extends Command {
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public RestartCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
