package telegram.command;

import menu.MainMenu;
import model.Client;
import model.ClientState;
import telegram.BlueAnonymousBot;

public class CancelCommand extends Command{
    private final Client client;
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";
    public CancelCommand(String chatId, Client client) {
        super(chatId);
        this.client=client;
    }


    @Override
    public void execute() {
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        client.setClientState(ClientState.NORMAL);
    }
}
