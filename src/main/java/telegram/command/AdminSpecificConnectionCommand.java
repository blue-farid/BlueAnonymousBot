package telegram.command;

import menu.CancelMenu;
import model.Client;
import model.ClientState;
import service.ClientService;
import telegram.BlueAnonymousBot;

@Admin
public class AdminSpecificConnectionCommand extends SpecificConnectionCommand{
    public AdminSpecificConnectionCommand(String chatId, Client client) {
        super(chatId, client);
    }

    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        ClientService.getInstance().setClientState(client, ClientState.ADMIN_SENDING_CONTACT_ID);
    }
}
