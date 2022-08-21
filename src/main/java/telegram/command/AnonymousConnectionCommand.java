package telegram.command;

import menu.ChooseContactSexMenu;
import model.Client;
import model.ClientState;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class AnonymousConnectionCommand extends Command {

    public AnonymousConnectionCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        if (this.client.getClientState() == ClientState.NORMAL) {
            this.sendMessage.setText(Property.MESSAGES_P.get("anonymous_connection"));
            this.sendMessage.setReplyMarkup(ChooseContactSexMenu.getInstance());
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
            ClientService.getInstance().setClientState(client, ClientState.CHOOSING_CONTACT_SEX);
        }
    }
}
