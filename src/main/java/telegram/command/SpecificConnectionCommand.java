package telegram.command;

import menu.CancelMenu;
import model.Client;
import model.ClientState;
import org.apache.log4j.MDC;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class SpecificConnectionCommand extends Command {

    protected final String localMessage;

    public SpecificConnectionCommand(Client client) {
        super(client);
        localMessage = Property.MESSAGES_P.get("specific_connection");
    }


    @Override
    public void execute() {
        addBaseLog();
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);

        ClientService.getInstance().setClientState(client, ClientState.SENDING_CONTACT_INFO);
    }
}
