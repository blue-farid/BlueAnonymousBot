package telegram.command;

import menu.MainMenu;
import telegram.BlueAnonymousBot;

public class CancelCommand extends Command{
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";
    public CancelCommand(String chatId) {
        super(chatId);
    }

    public CancelCommand(String chatId, String optionalCommand) {
        super(chatId, optionalCommand);
    }

    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
