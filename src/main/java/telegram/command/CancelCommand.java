package telegram.command;

import menu.MainMenu;
import model.Client;
import model.ClientState;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;

public class CancelCommand extends Command{
    private final String localMessage;

    public CancelCommand(Client client) {
        super(client);
        this.localMessage= Property.MESSAGES_P.get("cancel");
    }


    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        ClientService.getInstance().setClientState(client, ClientState.NORMAL);
    }
}
