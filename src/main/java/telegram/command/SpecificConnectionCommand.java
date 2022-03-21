package telegram.command;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import inlineMenu.InlineJoinChannelKeyBoard;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;

public class SpecificConnectionCommand extends Command {

    private final String localMessage;

    private Client client;

    public SpecificConnectionCommand(String chatId, Client client) {
        super(chatId);
        this.client = client;
        localMessage= Property.MESSAGES_P.get("specific_connection");
    }


    @Override
    public void execute() {

        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);

        ClientService.getInstance().setClientState(client, ClientState.SENDING_CONTACT_INFO);
    }
}
