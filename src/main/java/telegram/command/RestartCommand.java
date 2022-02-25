package telegram.command;

import menu.MainMenu;
import properties.Property;
import telegram.BlueAnonymousBot;

public class RestartCommand extends Command {
    private final String localMessage;

    public RestartCommand(String chatId) {
        super(chatId);
        localMessage= Property.MESSAGES_P.get("restart");
    }

    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
