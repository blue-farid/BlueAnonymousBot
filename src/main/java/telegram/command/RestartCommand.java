package telegram.command;

import menu.MainMenu;
import model.Client;
import properties.Property;
import telegram.BlueAnonymousBot;

public class RestartCommand extends Command {
    private final String localMessage;

    public RestartCommand(Client client) {
        super(client);
        localMessage= Property.MESSAGES_P.get("restart");
    }

    @Override
    public void execute() {
        addBaseLog();
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
