package telegram.command;

import menu.MainMenu;
import model.Client;
import model.ClientState;
import properties.Property;
import telegram.BlueAnonymousBot;

public class CancelCommand extends Command{
    private final Client client;
    private final String localMessage;

    public CancelCommand(String chatId, Client client) {
        super(chatId);
        this.client=client;
        this.localMessage= Property.MESSAGES_P.get("cancel");
    }


    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        client.setClientState(ClientState.NORMAL);
    }
}
