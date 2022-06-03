package telegram.command;

import menu.CancelMenu;
import model.Client;
import model.ClientState;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class SpecificConnectionCommand extends Command {

    protected final String localMessage;

    protected final Client client;

    public SpecificConnectionCommand(String chatId, Client client) {
        super(chatId);
        this.client = client;
        localMessage = Property.MESSAGES_P.get("specific_connection");
    }


    @Override
    public void execute() {

        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);

        ClientService.getInstance().setClientState(client, ClientState.SENDING_CONTACT_INFO);
    }
}
