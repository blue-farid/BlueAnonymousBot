package telegram.command;

import menu.ChooseContactSexMenu;
import model.Client;
import model.ClientState;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;

public class AnonymousConnectionCommand extends Command {
    private final Client client;

    public AnonymousConnectionCommand(String chatId, Client client) {
        super(chatId);
        this.client = client;
    }


    @Override
    public void execute() {
        if (this.client.getClientState() == ClientState.NORMAL) {
            this.sendMessage.setText(Property.COMMANDS_P.get("anonymous_connection"));
            this.sendMessage.setReplyMarkup(ChooseContactSexMenu.getInstance());
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
            ClientService.getInstance().setClientState(client, ClientState.CHOOSING_CONTACT_SEX);
        }
    }
}
