package telegram.command;

import menu.MainMenu;
import telegram.BlueAnonymousBot;

public class RestartCommand extends Command {
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public RestartCommand() {
        super(localMessage);
    }

    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
