package telegram.command;

import menu.ChooseContactSexMenu;
import model.Client;
import model.ClientState;
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
            this.sendMessage.setText(BlueAnonymousBot.getInstance()
                    .getProperty("message.anonymous_connection"));
            this.sendMessage.setReplyMarkup(ChooseContactSexMenu.getInstance());
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
            client.setClientState(ClientState.CHOOSING_CONTACT_SEX);
        }
    }
}
