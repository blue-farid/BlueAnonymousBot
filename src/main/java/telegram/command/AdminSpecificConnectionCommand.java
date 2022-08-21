package telegram.command;

import menu.CancelMenu;
import model.Client;
import model.ClientState;
import service.ClientService;
import telegram.BlueAnonymousBot;

@Admin
public class AdminSpecificConnectionCommand extends SpecificConnectionCommand {
    public AdminSpecificConnectionCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() throws IllegalAccessException {
        checkAnnotations();
        addBaseLog();
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        ClientService.getInstance().setClientState(client, ClientState.ADMIN_SENDING_CONTACT_ID);
    }
}
